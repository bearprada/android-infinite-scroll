package lab.prada.android.ui.readmore;

import lab.prada.android.ui.readmore.adapter.ReadMoreAdapter;
import lab.prada.android.ui.readmore.adapter.ReadMoreAdapter.PullDownToRefreshListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

public class DemoGridActivity extends Activity implements PullDownToRefreshListener {

    private GridView mGridView;
    private ReadMoreAdapter<SampleAdapter> mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_grid);
        mGridView = (GridView)findViewById(R.id.gridView1);
        mAdapter = new ReadMoreAdapter<SampleAdapter>(this,
                new SampleAdapter(this), 128, 128);
        mAdapter.addListener(this);
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_grid, menu);
        return true;
    }

    @Override
    public void pullDownToRefresh() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.getAdapter().addCount(5);
                mAdapter.handledRefresh();
            }
        });
    }

}
