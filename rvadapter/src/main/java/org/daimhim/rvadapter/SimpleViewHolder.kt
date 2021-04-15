package org.daimhim.rvadapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

open class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Views indexed with their IDs
     */
    private val views: SparseArray<View> = SparseArray()

    var mRecyclerClickListener: RecyclerContract.RecyclerClickListener? = null
    var mRecyclerLongClickListener: RecyclerContract.RecyclerLongClickListener? = null


    /**
     * 执行点击事件
     *
     * @param view              需要设置点击事件的View
     * @param recyclerViewClick Adapter对象
     * @return 是否set成功
     */
    fun performItemClick(view: View, recyclerViewClick: RecyclerViewEmpty<SimpleViewHolder>?): Boolean {
        //保证一个ViewHolder只有一个OnClickListener对象 通过getLayoutPosition（）
        if (mRecyclerClickListener == null) {
            mRecyclerClickListener = RecyclerContract.RecyclerClickListener()
        }
        mRecyclerClickListener?.setPositionRecyclerView(recyclerViewClick, adapterPosition)
        view.setOnClickListener(mRecyclerClickListener)
        return true
    }

    /**
     * 执行点击事件
     *
     * @param view              需要设置点击事件的View
     * @param recyclerViewClick Adapter对象
     * @return is set success
     */
    fun performLongItemClick(
        view: View,recyclerViewClick:RecyclerViewEmpty<SimpleViewHolder>?): Boolean {
        if (mRecyclerLongClickListener == null) {
            mRecyclerLongClickListener = RecyclerContract.RecyclerLongClickListener()
        }
        mRecyclerLongClickListener?.setPositionRecyclerView(recyclerViewClick, adapterPosition)
        view.setOnLongClickListener(mRecyclerLongClickListener)
        return true
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

    fun <T : ViewDataBinding> getBinding():T? {
        return DataBindingUtil.getBinding(itemView)
    }
}