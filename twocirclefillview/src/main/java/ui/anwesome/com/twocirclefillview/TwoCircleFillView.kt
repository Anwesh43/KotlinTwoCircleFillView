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
}