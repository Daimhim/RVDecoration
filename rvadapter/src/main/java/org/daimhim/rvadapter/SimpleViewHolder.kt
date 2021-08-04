package org.daimhim.rvadapter

import android.util.SparseArray
import android.view.View
import androidx.annotation.*
import androidx.recyclerview.widget.RecyclerView

open class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Views indexed with their IDs
     */
    private val views: SparseArray<View> = SparseArray()

    var recyclerClickListener: RecyclerContract.RecyclerClickListener? = null
    var recyclerLongClickListener: RecyclerContract.RecyclerLongClickListener? = null
    var bindingPosition = -1

    /**
     * 执行点击事件
     *
     * @param view              需要设置点击事件的View
     * @param recyclerViewClick Adapter对象
     * @return 是否set成功
     */
    @Suppress("UNCHECKED_CAST")
    fun performItemClick(view: View): Boolean {
        //保证一个ViewHolder只有一个OnClickListener对象 通过getLayoutPosition（）
        if (recyclerClickListener == null) {
            recyclerClickListener = RecyclerContract.RecyclerClickListener()
        }
        val position = if (bindingPosition > 0) bindingPosition else bindingAdapterPosition
        recyclerClickListener?.setPositionRecyclerView(bindingAdapter as RecyclerViewEmpty<SimpleViewHolder>, position)
        view.setOnClickListener(recyclerClickListener)
        return true
    }
    fun performItemClick(id:Int){
        performItemClick(itemView.findViewById<View>(id))
    }
    /**
     * 执行点击事件
     *
     * @param view              需要设置点击事件的View
     * @param recyclerViewClick Adapter对象
     * @return is set success
     */
    @Suppress("UNCHECKED_CAST")
    fun performLongItemClick(view: View): Boolean {
        if (recyclerLongClickListener == null) {
            recyclerLongClickListener = RecyclerContract.RecyclerLongClickListener()
        }
        val position = if (bindingPosition > 0) bindingPosition else bindingAdapterPosition
        recyclerLongClickListener?.setPositionRecyclerView(bindingAdapter as RecyclerViewEmpty<SimpleViewHolder>, position)
        view.setOnLongClickListener(recyclerLongClickListener)
        return true
    }

    fun performLongItemClick(id:Int){
        performLongItemClick(itemView.findViewById<View>(id))
    }

    /**
     * 清理数据
     */
    fun clear(){
        recyclerClickListener = null
        recyclerLongClickListener = null
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getViewOrNull(@IdRes viewId: Int): T? {
        val view = views.get(viewId)
        if (view == null) {
            itemView.findViewById<T>(viewId)?.let {
                views.put(viewId, it)
                return it
            }
        }
        return view as? T
    }

    fun <T : View> Int.findView(): T? {
        return itemView.findViewById(this)
    }


    @Suppress("UNCHECKED_CAST")
    open fun <T : View> getView(@IdRes viewId: Int): T? {
        val view = views.get(viewId)
        if (view == null) {
            itemView.findViewById<T>(viewId)?.let {
                views.put(viewId, it)
                return it
            }
        }
        return view as? T
    }
}