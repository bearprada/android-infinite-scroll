package lab.prada.android.ui.infinitescroll.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import lab.prada.android.ui.mymodule.app.R;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,
                        DemoListActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,
                        DemoGridActivity.class);
                startActivity(intent);
            }
        });
    }
}
