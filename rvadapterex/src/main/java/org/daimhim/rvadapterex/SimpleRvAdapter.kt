package org.daimhim.rvadapterex

import android.view.LayoutInflater
import android.view.ViewGroup
import org.daimhim.rvadapter.RecyclerContract
import org.daimhim.rvadapter.RecyclerViewEmpty
import org.daimhim.rvadapter.SimpleViewHolder

abstract class SimpleRvAdapter<T> : RecyclerViewEmpty<SimpleViewHolder<T>>(),
    RecyclerContract.SimpleContract<MutableList<T>,T> {
    val data = mutableListOf<T>()
    override fun onLoad(ts: MutableList<T>?) {
        ts?.let {
            data.addAll(it)
            notifyItemRangeInserted(data.size-1,it.size)
        }
    }

    override fun getItem(position: Int): T {
        return data[position]
    }

    override fun onRefresh(ts: MutableList<T>?) {
        ts?.let {
            data.clear()
            data.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun getDataItemCount(): Int {
        return data.size
    }

    override fun getEmptyViewType(): Int {
        return Int.MAX_VALUE
    }
    override fun isEmptyView(): Boolean {
        return data.isEmpty()
    }

    override fun onCreateDataViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<T> {
        return SimpleViewHolder<T>(LayoutInflater.from(parent.context).inflate(onCreateDataViewHolder(viewType),parent,false))
    }

    abstract fun onCreateDataViewHolder(viewType: Int):Int

    override fun onCreateEmptyViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<T> {
        return SimpleViewHolder<T>(LayoutInflater.from(parent.context).inflate(R.layout.empty_view_holder,parent,false))
    }

}