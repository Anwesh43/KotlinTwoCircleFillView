package ui.anwesome.com.twocirclefillview

/**
 * Created by anweshmishra on 12/02/18.
 */
import android.app.Activity
import android.graphics.*
import android.content.*
import android.view.*

class TwoCircleFillView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    var circleFillListener:CircleFillListener?=null
    fun addCircleFillListener(onFill: (Int) -> Unit) {
        circleFillListener = CircleFillListener(onFill)
    }
    override fun draw(canvas:Canvas) {
        renderer.render(canvas, paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class TwoCircleFill(var x:Float,var y:Float,var size:Float) {
        val state = TwoCircleFillState()
        fun draw(canvas:Canvas,paint:Paint) {
            paint.color = Color.parseColor("#40a4df")
            paint.strokeWidth = size/55
            paint.strokeCap = Paint.Cap.ROUND
            canvas.drawClippedCircle(x-size/2,y,size/6,1-state.scales[0],paint)

            paint.style = Paint.Style.STROKE
            canvas.drawRect(x-size/3,y-size/20,x+size/3,y+size/20,paint)
            paint.style = Paint.Style.FILL
            canvas.drawRect(x-size/3+(2*size/3)*(state.scales[1]),y-size/20,x-size/3+(2*size/3)*state.scales[0],y+size/20,paint)
            canvas.drawClippedCircle(x+size/2,y,size/6,state.scales[1],paint)
        }
        fun update(stopcb:(Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:() -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class TwoCircleFillState(var prevScale:Float = 0f,var dir:Float = 0f,var j:Int = 0,var jDir:Int = 1) {
        val scales:Array<Float> = arrayOf(0f,0f)
        fun update(stopcb: (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if(Math.abs(scales[j] - prevScale) > 1) {
                this.scales[j] = prevScale + dir
                j += jDir
                if(j == scales.size || j == -1) {
                    jDir *= -1
                    j += jDir
                    prevScale = this.scales[j]
                    dir = 0f
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb: () -> Unit) {
            if(this.dir == 0f) {
                this.dir = 1f - 2*this.prevScale
                startcb()
            }
        }
    }
    data class Animator(var view:View, var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class Renderer(var view:TwoCircleFillView, var time:Int = 0) {
        val animator = Animator(view)
        var twoCircleFill:TwoCircleFill?=null
        fun render(canvas:Canvas, paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                twoCircleFill = TwoCircleFill(w/2,h/2,  2*Math.min(w,h)/3)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            twoCircleFill?.draw(canvas,paint)
            time++
            animator.animate {
                twoCircleFill?.update {
                    animator.stop()
                    when(it) {
                        0f -> view.circleFillListener?.onFillListener?.invoke(1)
                        1f -> view.circleFillListener?.onFillListener?.invoke(2)
                    }
                }
            }
        }
        fun handleTap() {
            twoCircleFill?.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity:Activity):TwoCircleFillView {
            val view = TwoCircleFillView(activity)
            activity.setContentView(view)
            return view
        }
    }
    data class CircleFillListener(var onFillListener:(Int)->Unit)
}
fun Canvas.drawClippedCircle(x:Float,y:Float,r:Float,scale:Float,paint:Paint) {
    save()
    translate(x,y)
    paint.style = Paint.Style.FILL
    save()
    val path = Path()
    path.addCircle(0f,0f,r,Path.Direction.CW)
    clipPath(path)
    drawRect(RectF(-r, 3*r/10 - (r+3*r/10)*scale, r, r),paint)
    restore()
    paint.style = Paint.Style.STROKE
    drawCircle(0f,0f,r,paint)
    restore()
}