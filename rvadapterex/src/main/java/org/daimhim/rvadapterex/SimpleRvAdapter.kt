package org.daimhim.rvadapterex

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import org.daimhim.rvadapter.RecyclerContract
import org.daimhim.rvadapter.RecyclerViewEmpty
import org.daimhim.rvadapter.SimpleViewHolder

abstract class SimpleRvAdapter<T> : RecyclerViewEmpty<SimpleViewHolder>(),
    RecyclerContract.SimpleContract<MutableList<T>,T> {
    /**
     * 空布局资源 可set设置
     */
    var emptyLayout : Int = -1
    protected val EMPTY_LAYOUT = Int.MAX_VALUE;

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

    @SuppressLint("NotifyDataSetChanged")
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
        val b = getEmptyLayoutResId(EMPTY_LAYOUT) != -1 && dataItemCount == 0
        return b
    }

    override fun onCreateDataViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        var itemView = LayoutInflater.from(parent.context)
            .inflate(onCreateDataViewHolder(viewType), parent, false)
        if (isDataBinding(viewType)){
            itemView = DataBindingUtil.bind<ViewDataBinding>(itemView)?.root
        }
        return SimpleViewHolder(itemView)
    }

    abstract fun onCreateDataViewHolder(viewType: Int):Int

    override fun onCreateEmptyViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        var emptyId = getEmptyLayoutResId(viewType)
        if (emptyId == -1){
            emptyId = R.layout.empty_view_holder;
        }
        var inflate = LayoutInflater.from(parent.context).inflate(emptyId, parent, false)
        if (isDataBinding(viewType)){
            inflate = DataBindingUtil.bind<ViewDataBinding>(inflate)?.root
        }
        return SimpleViewHolder(inflate)
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

    /**
     * 空布局资源
     */
    open fun getEmptyLayoutResId(viewType: Int):Int{
        return emptyLayout
    }
}

fun MutableList<*>.includeIndex(index: Int): Boolean {
    if (index < 0 || index > size || index == size) {
        return false
    }
    return true
}