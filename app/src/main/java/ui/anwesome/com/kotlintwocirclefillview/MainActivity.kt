package ui.anwesome.com.kotlintwocirclefillview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.twocirclefillview.TwoCircleFillView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TwoCircleFillView.create(this)
    }
}
