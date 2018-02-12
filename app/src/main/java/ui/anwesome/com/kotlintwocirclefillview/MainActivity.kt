package ui.anwesome.com.kotlintwocirclefillview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ui.anwesome.com.twocirclefillview.TwoCircleFillView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = TwoCircleFillView.create(this)
        view.addCircleFillListener {
            Toast.makeText(this,"$it filled",Toast.LENGTH_SHORT).show()
        }
    }
}
