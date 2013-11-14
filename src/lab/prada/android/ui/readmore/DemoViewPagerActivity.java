package lab.prada.android.ui.readmore;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DemoViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view_pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_view_pager, menu);
        return true;
    }

}
