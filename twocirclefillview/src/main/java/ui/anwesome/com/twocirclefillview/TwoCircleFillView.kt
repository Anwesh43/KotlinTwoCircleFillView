package ui.anwesome.com.twocirclefillview

/**
 * Created by anweshmishra on 12/02/18.
 */
import android.graphics.*
import android.content.*
import android.view.*

class TwoCircleFillView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun draw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class TwoCircleFill(var x:Float,var y:Float,var size:Float) {
        val state = TwoCircleFillState()
        fun draw(canvas:Canvas,paint:Paint) {
            paint.color = Color.parseColor("#f44336")
            paint.strokeWidth = size/20
            paint.strokeCap = Paint.Cap.ROUND
            canvas.drawClippedCircle(x-size/2,y,size/6,1-state.scales[0],paint)
            canvas.drawLine(x-size/3+(2*size/3)*(state.scales[1]),y,x-size/3+(2*size/3)*state.scales[0],y,paint)
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
}
fun Canvas.drawClippedCircle(x:Float,y:Float,r:Float,scale:Float,paint:Paint) {
    save()
    translate(x,y)
    val path = Path()
    path.addCircle(0f,0f,r,Path.Direction.CW)
    clipPath(path)
    drawRect(RectF(-r, r - 2*r*scale, r, r),paint)
    restore()
}