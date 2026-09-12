package lab.prada.android.ui.infinitescroll.sample

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.GridView
import lab.prada.android.ui.lab.prada.android.ui.infinitescroll.InfiniteScrollAdapter
import lab.prada.android.ui.mymodule.app.R

class DemoGridActivity : Activity(), InfiniteScrollAdapter.InfiniteScrollListener {
    private companion object {
        const val GRID_ITEM_HEIGHT = 128
        const val GRID_ITEM_WIDTH = 128
    }

    private lateinit var gridView: GridView
    private lateinit var adapter: InfiniteScrollAdapter<SampleAdapter>
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_grid)

        gridView = findViewById(R.id.gridView1)
        adapter = InfiniteScrollAdapter(
            this,
            SampleAdapter(this),
            GRID_ITEM_WIDTH,
            GRID_ITEM_HEIGHT
        )
        adapter.addListener(this)
        gridView.adapter = adapter
    }

    override fun onInfiniteScrolled() {
        handler.postDelayed({
            adapter.getOriginalAdapter().addCount(5)
            adapter.handledRefresh()
            if (adapter.getOriginalAdapter().count >= 100) {
                adapter.canReadMore(false)
            }
        }, 3000)
    }
}
