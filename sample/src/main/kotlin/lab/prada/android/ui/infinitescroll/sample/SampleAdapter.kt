package lab.prada.android.ui.infinitescroll.sample

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button

class SampleAdapter(private val context: Context) : BaseAdapter() {
    private var count = 50

    override fun getCount(): Int = count

    fun addCount(number: Int) {
        count += number
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any = position

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        Button(context).apply {
            text = "button $position"
        }
}
