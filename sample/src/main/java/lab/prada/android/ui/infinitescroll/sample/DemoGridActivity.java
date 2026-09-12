package lab.prada.android.ui.infinitescroll.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.GridView;

import lab.prada.android.ui.lab.prada.android.ui.infinitescroll.InfiniteScrollAdapter;
import lab.prada.android.ui.mymodule.app.R;

public class DemoGridActivity extends Activity implements InfiniteScrollAdapter.InfiniteScrollListener {

    private static final int GRID_ITEM_HEIGHT = 128;
    private static final int GRID_ITEM_WIDTH = 128;
    private GridView mGridView;
    private InfiniteScrollAdapter<SampleAdapter> mAdapter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_grid);
        mGridView = (GridView) findViewById(R.id.gridView1);
        mAdapter = new InfiniteScrollAdapter<SampleAdapter>(this,
                new SampleAdapter(this), GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT);
        mAdapter.addListener(this);
        mGridView.setAdapter(mAdapter);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onInfiniteScrolled() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.getAdapter().addCount(5);
                mAdapter.handledRefresh();
                if (mAdapter.getOriginalAdapter().getCount() > 100) {
                    mAdapter.canReadMore(false);
                }
            }
        }, 3000);
    }
}
