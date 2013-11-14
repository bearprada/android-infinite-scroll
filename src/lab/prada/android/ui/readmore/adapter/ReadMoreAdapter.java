package lab.prada.android.ui.readmore.adapter;

import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lab.prada.android.ui.readmore.R;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

public class ReadMoreAdapter<T extends BaseAdapter> extends BaseAdapter {

    private final T mAdapter;
    private final View mProgressView;
    private Vector<PullDownToRefreshListener> mListeners = new Vector<PullDownToRefreshListener>();
    
    private final static int NONE_STATE = 0;
    private final static int REFRESHING_STATE = 1;
    private int state = NONE_STATE;
    
    private final ThreadPoolExecutor tpe = new ThreadPoolExecutor(4, 16, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public interface PullDownToRefreshListener {
        public void pullDownToRefresh();
    }

    public ReadMoreAdapter(Context context, T adapter, int itemWidth,
            int itemHeight) {
        mAdapter = adapter;
        final LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout progress = (LinearLayout) inflater.inflate(
                R.layout.grid_item_progress, null);
        progress.setLayoutParams(new GridView.LayoutParams(itemWidth,
                itemHeight));
        progress.setPadding(0, 0, 0, 0);
        progress.setGravity(Gravity.CENTER);
        progress.setVisibility(View.VISIBLE);
        mProgressView = progress;
    }

    public ReadMoreAdapter(T adapter, View progressView) {
        mAdapter = adapter;
        mProgressView = progressView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mAdapter.areAllItemsEnabled();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return (isProgressViewPosition(position)) ? null : mAdapter
                .getDropDownView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return (isProgressViewPosition(position)) ? Adapter.IGNORE_ITEM_VIEW_TYPE
                : mAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return mAdapter.getViewTypeCount();
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    @Override
    public boolean isEnabled(int position) {
        return (isProgressViewPosition(position)) ? false : mAdapter
                .isEnabled(position);
    }

    @Override
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        mAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return shouldShowProgressView() ? mAdapter.getCount() + 1 : mAdapter
                .getCount();
    }

    @Override
    public Object getItem(int position) {
        return (isProgressViewPosition(position)) ? null : mAdapter
                .getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return (isProgressViewPosition(position)) ? -1 : mAdapter
                .getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isProgressViewPosition(position)) {
            if (isRefreshing() == false) {
                state = REFRESHING_STATE;
                tpe.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mListeners != null) {
                            for (PullDownToRefreshListener listener : mListeners) {
                                listener.pullDownToRefresh();
                            }
                        }
                    }
                });
            }
            if (mProgressView.getVisibility() == View.GONE) {
                mProgressView.setVisibility(View.VISIBLE);
            }
            return mProgressView;
        } else {
            return mAdapter.getView(position, convertView, parent);
        }
    }

    public T getOriginalAdapter() {
        return mAdapter;
    }

    public void removeListener(PullDownToRefreshListener listener) {
        if (listener != null) {

        }
    }

    public void addListener(PullDownToRefreshListener listener) {
        if (listener != null && mListeners.contains(listener) == false) {
            mListeners.add(listener);
        }
    }

    public boolean isProgressViewPosition(int position) {
        return shouldShowProgressView() && position == getCount() - 1;
    }

    private boolean shouldShowProgressView() {
        return mAdapter.getCount() > 0 /* && TextUtils.isEmpty(mNextPage) == false */;
    }

    public boolean isRefreshing() {
        return state == REFRESHING_STATE;
    }

    public void handledRefresh() {
        assert(Looper.myLooper().equals(Looper.getMainLooper()));
        if (isRefreshing()) {
            mProgressView.setVisibility(View.GONE);
            state = NONE_STATE;
        }
    }

    public T getAdapter() {
        return mAdapter;
    }
}
