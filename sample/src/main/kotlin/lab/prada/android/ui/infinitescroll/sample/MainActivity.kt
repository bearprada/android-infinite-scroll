package lab.prada.android.ui.infinitescroll.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import lab.prada.android.ui.mymodule.app.R

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        findViewById<View>(R.id.button1).setOnClickListener {
            startActivity(Intent(this, DemoListActivity::class.java))
        }
        findViewById<View>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, DemoGridActivity::class.java))
        }
    }
}
