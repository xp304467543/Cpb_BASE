package com.lib.basiclib.widget.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fh.basemodle.R
import com.lib.basiclib.utils.ViewUtils

/**

 * @since 2019/1/25 20:34
 *
 * 列表Popup
 */
class RecyclerPopupWindow(context: Context, data: List<String>) : BasePopupWindow(context, R.layout.base_popup_recycler) {

    init {
        width = ViewUtils.getScreenWidth() / 3
        val recyclerView = findView<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PopupAdapter(data)
    }

    private var mListener: ((position: Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        mListener = listener
    }

    inner class PopupAdapter(private val data: List<String>) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.base_popup_item, parent, false))
        }

        override fun getItemCount(): Int = data.size
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvPopItem.text = data[position]
            holder.itemView.setOnClickListener {
                mListener?.invoke(position)
                dismiss()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPopItem: TextView = itemView.findViewById(R.id.tvPopItem)
    }

}