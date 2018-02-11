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
        fun draw(canvas:Canvas,paint:Paint) {
            paint.color = Color.parseColor("#f44336")
            paint.strokeWidth = size/20
            paint.strokeCap = Paint.Cap.ROUND
            canvas.drawClippedCircle(x-size/2,y,size/6,1f,paint)
            canvas.drawLine(x-size/3+2*size/3,y,x-size/3+2*size/3,y,paint)
            canvas.drawClippedCircle(x+size/2,y,size/6,1f,paint)
        }
        fun update(stopcb:(Float) -> Unit) {

        }
        fun startUpdating(startcb:() -> Unit) {

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