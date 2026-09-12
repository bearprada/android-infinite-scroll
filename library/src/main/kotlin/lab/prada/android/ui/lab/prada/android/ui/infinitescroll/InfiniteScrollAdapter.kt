package lab.prada.android.ui.lab.prada.android.ui.infinitescroll

import android.content.Context
import android.database.DataSetObserver
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.RelativeLayout
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

/** Adds a progress row to a [BaseAdapter] and notifies listeners when it is displayed. */
class InfiniteScrollAdapter<T : BaseAdapter> : BaseAdapter {
    private val adapter: T
    private val progressView: View
    private val listeners = CopyOnWriteArrayList<InfiniteScrollListener>()
    private val state = AtomicInteger(NONE_STATE)

    @Volatile
    private var canReadMoreEnabled = true

    interface InfiniteScrollListener {
        fun onInfiniteScrolled()
    }

    constructor(context: Context, adapter: T, progressView: View) {
        this.adapter = adapter
        this.progressView = progressView
    }

    constructor(context: Context, adapter: T, itemWidth: Int, itemHeight: Int) {
        this.adapter = adapter
        this.progressView = RelativeLayout(context).apply {
            layoutParams = AbsListView.LayoutParams(itemWidth, itemHeight)
            gravity = Gravity.CENTER
            addView(ProgressBar(context))
        }
    }

    constructor(adapter: T, progressView: View) {
        this.adapter = adapter
        this.progressView = progressView
    }

    override fun areAllItemsEnabled(): Boolean = adapter.areAllItemsEnabled()

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? = if (isProgressViewPosition(position)) {
        null
    } else {
        adapter.getDropDownView(position, convertView, parent)
    }

    override fun getItemViewType(position: Int): Int = if (isProgressViewPosition(position)) {
        Adapter.IGNORE_ITEM_VIEW_TYPE
    } else {
        adapter.getItemViewType(position)
    }

    override fun getViewTypeCount(): Int = adapter.viewTypeCount

    override fun hasStableIds(): Boolean = adapter.hasStableIds()

    override fun isEmpty(): Boolean = adapter.isEmpty

    override fun registerDataSetObserver(observer: DataSetObserver) {
        adapter.registerDataSetObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        adapter.unregisterDataSetObserver(observer)
    }

    override fun getCount(): Int = if (shouldShowProgressView()) {
        adapter.count + 1
    } else {
        adapter.count
    }

    override fun getItem(position: Int): Any? = if (isProgressViewPosition(position)) {
        null
    } else {
        adapter.getItem(position)
    }

    override fun getItemId(position: Int): Long = if (isProgressViewPosition(position)) {
        -1L
    } else {
        adapter.getItemId(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (!isProgressViewPosition(position)) {
            return adapter.getView(position, convertView, parent)
        }

        if (state.compareAndSet(NONE_STATE, REFRESHING_STATE)) {
            listeners.forEach(InfiniteScrollListener::onInfiniteScrolled)
        }

        if (progressView.visibility == View.GONE) {
            progressView.visibility = View.VISIBLE
        }
        return progressView
    }

    fun removeListener(listener: InfiniteScrollListener?) {
        if (listener != null) {
            listeners.remove(listener)
        }
    }

    fun addListener(listener: InfiniteScrollListener?) {
        if (listener != null) {
            listeners.addIfAbsent(listener)
        }
    }

    fun isProgressViewPosition(position: Int): Boolean =
        shouldShowProgressView() && position == count - 1

    fun canReadMore(enable: Boolean) {
        canReadMoreEnabled = enable
        notifyDataSetChanged()
    }

    fun isRefreshing(): Boolean = state.get() == REFRESHING_STATE

    fun handledRefresh() {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "handledRefresh() must be called on the main thread"
        }
        if (state.compareAndSet(REFRESHING_STATE, NONE_STATE)) {
            progressView.visibility = View.GONE
        }
    }

    fun getOriginalAdapter(): T = adapter

    fun getAdapter(): T = adapter

    private fun shouldShowProgressView(): Boolean = adapter.count > 0 && canReadMoreEnabled

    private companion object {
        const val NONE_STATE = 0
        const val REFRESHING_STATE = 1
    }
}
