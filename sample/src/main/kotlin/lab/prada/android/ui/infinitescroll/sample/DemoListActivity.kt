package lab.prada.android.ui.infinitescroll.sample

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.AbsListView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import lab.prada.android.ui.lab.prada.android.ui.infinitescroll.InfiniteScrollAdapter
import lab.prada.android.ui.mymodule.app.R

class DemoListActivity : Activity(), InfiniteScrollAdapter.InfiniteScrollListener {
    private lateinit var adapter: InfiniteScrollAdapter<SampleAdapter>
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_list)

        val progress = RelativeLayout(this).apply {
            layoutParams = AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 100)
            gravity = Gravity.CENTER
            addView(ProgressBar(this@DemoListActivity))
        }

        adapter = InfiniteScrollAdapter(this, SampleAdapter(this), progress)
        adapter.addListener(this)
        findViewById<ListView>(R.id.listView).adapter = adapter
    }

    override fun onInfiniteScrolled() {
        handler.postDelayed({
            adapter.getOriginalAdapter().addCount(5)
            adapter.handledRefresh()
        }, 3000)
    }
}
