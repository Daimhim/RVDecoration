package org.daimhim.rvadapterex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.daimhim.rvadapter.RecyclerContract
import org.daimhim.rvadapter.RecyclerViewEmpty
import org.daimhim.rvadapter.SimpleViewHolder

abstract class SimpleRvAdapter<T> : RecyclerViewEmpty<SimpleViewHolder>(),
    RecyclerContract.SimpleContract<MutableList<T>,T> {
    val data = mutableListOf<T>()
    override fun onLoad(ts: MutableList<T>?) {
        ts?.let {
            data.addAll(it)
            notifyItemRangeInserted(data.size-1,it.size)
        }
    }

    override fun getItem(position: Int): T? {
        if (data.includeIndex(position)) {
            return data[position]
        }
        return null
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

    override fun onCreateDataViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        if (isDataBinding(viewType)){

        }
        return SimpleViewHolder(LayoutInflater.from(parent.context).inflate(onCreateDataViewHolder(viewType),parent,false))
    }

    abstract fun onCreateDataViewHolder(viewType: Int):Int

    override fun onCreateEmptyViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.empty_view_holder,parent,false))
    }
    /**
     * 是否开启 DataBinding
     *  默认开启
     */
    open fun isDataBinding(viewType: Int):Boolean{
        return true
    }

    /**
     * 扩展方法 获取ViewDataBinding
     */
    fun <D : ViewDataBinding> SimpleViewHolder.getDataBinding():D?{
        return DataBindingUtil.getBinding(itemView)
    }
}

fun MutableList<*>.includeIndex(index: Int): Boolean {
    if (index < 0 || index > size || index == size) {
        return false
    }
    return true
}