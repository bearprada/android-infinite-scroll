package lab.prada.android.ui.readmore;

import lab.prada.android.ui.readmore.adapter.ReadMoreAdapter;
import lab.prada.android.ui.readmore.adapter.ReadMoreAdapter.PullDownToRefreshListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class DemoListActivity extends Activity implements PullDownToRefreshListener {

    private static final int DEFAULT_ITEM_WIDTH = 128;
    private static final int DEFAULT_ITEM_HEIGHT = 128;

    private ReadMoreAdapter<SampleAdapter> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);

        mAdapter = new ReadMoreAdapter<SampleAdapter>(this,
                new SampleAdapter(this), DEFAULT_ITEM_WIDTH, DEFAULT_ITEM_HEIGHT);
        mAdapter.addListener(this);

        ((ListView)this.findViewById(R.id.listView)).setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_list, menu);
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
