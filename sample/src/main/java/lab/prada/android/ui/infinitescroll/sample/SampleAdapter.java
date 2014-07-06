package lab.prada.android.ui.infinitescroll.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class SampleAdapter extends BaseAdapter {
    private Context mContext;
    private int count = 50;

    public SampleAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void addCount(int num) {
        count += num;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Button btn = new Button(mContext);
        btn.setText("button " + arg0);
        return btn;
    }

}