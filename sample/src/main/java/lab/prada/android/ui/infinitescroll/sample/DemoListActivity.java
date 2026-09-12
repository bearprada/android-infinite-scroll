package lab.prada.android.ui.infinitescroll.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import lab.prada.android.ui.lab.prada.android.ui.infinitescroll.InfiniteScrollAdapter;
import lab.prada.android.ui.mymodule.app.R;

public class DemoListActivity extends Activity implements InfiniteScrollAdapter.InfiniteScrollListener {

    private InfiniteScrollAdapter<SampleAdapter> mAdapter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);

        RelativeLayout progress = new RelativeLayout(this);
        progress.setLayoutParams(new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT, 100));
        progress.setGravity(Gravity.CENTER);
        progress.addView(new ProgressBar(this));

        mAdapter = new InfiniteScrollAdapter<SampleAdapter>(this,
                new SampleAdapter(this), progress);
        mAdapter.addListener(this);
        mHandler = new Handler(Looper.getMainLooper());
        ((ListView) findViewById(R.id.listView)).setAdapter(mAdapter);
    }

    @Override
    public void onInfiniteScrolled() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.getAdapter().addCount(5);
                mAdapter.handledRefresh();
            }
        }, 3000);
    }
}
