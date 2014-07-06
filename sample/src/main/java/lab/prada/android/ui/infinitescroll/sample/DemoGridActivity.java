package lab.prada.android.ui.infinitescroll.sample;

import lab.prada.android.ui.infinitescroll.InfiniteScrollAdapter;
import lab.prada.android.ui.infinitescroll.InfiniteScrollAdapter.InfiniteScrollListener;
import lab.prada.android.ui.readmore.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.GridView;

public class DemoGridActivity extends Activity implements InfiniteScrollListener {

    private static final int GRID_ITEM_HEIGHT = 128;
    private static final int GRID_ITEM_WIDTH = 128;
    private GridView mGridView;
    private InfiniteScrollAdapter<SampleAdapter> mAdapter;
    private Handler mHandler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_grid);
        mGridView = (GridView)findViewById(R.id.gridView1);
        mAdapter = new InfiniteScrollAdapter<SampleAdapter>(this,
                new SampleAdapter(this), GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT);
        mAdapter.addListener(this);
        mGridView.setAdapter(mAdapter);
        mHandler = new Handler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_grid, menu);
        return true;
    }

    @Override
    public void onInfiniteScrolled() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.getAdapter().addCount(5);
                mAdapter.handledRefresh();
                // when the adapter load more then 100 items. i will disable the
                // feature of load more.
                if (mAdapter.getOriginalAdapter().getCount() > 100) {
                    mAdapter.canReadMore(false);
                }
            }
        }, 3000);
    }

}
