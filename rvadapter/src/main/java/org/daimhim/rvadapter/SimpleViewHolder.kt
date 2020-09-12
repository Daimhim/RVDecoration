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


    open fun <T : View> getView(@IdRes viewId: Int): T {
        val view = getViewOrNull<T>(viewId)
        checkNotNull(view) { "No view found with id $viewId" }
        return view
    }

    @Suppress("UNCHECKED_CAST")
    open fun <T : View> getViewOrNull(@IdRes viewId: Int): T? {
        val view = views.get(viewId)
        if (view == null) {
            itemView.findViewById<T>(viewId)?.let {
                views.put(viewId, it)
                return it
            }
        }
        return view as? T
    }

    open fun <T : View> Int.findView(): T? {
        return itemView.findViewById(this)
    }

    open fun setText(@IdRes viewId: Int, value: CharSequence?) {
        getView<TextView>(viewId).text = value
    }

    open fun setText(@IdRes viewId: Int, @StringRes strId: Int) {
        getView<TextView>(viewId).setText(strId)
    }

    open fun setTextColor(@IdRes viewId: Int, @ColorInt color: Int) {
        getView<TextView>(viewId).setTextColor(color)
    }

    open fun setTextColorRes(@IdRes viewId: Int, @ColorRes colorRes: Int) {
        getView<TextView>(viewId).setTextColor(ContextCompat.getColor(itemView.context, colorRes))
    }

    open fun setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int) {
        getView<ImageView>(viewId).setImageResource(imageResId)
    }

    open fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?) {
        getView<ImageView>(viewId).setImageDrawable(drawable)
    }

    open fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?) {
        getView<ImageView>(viewId).setImageBitmap(bitmap)
    }

    open fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int) {
        getView<View>(viewId).setBackgroundColor(color)
    }

    open fun setBackgroundResource(@IdRes viewId: Int, @DrawableRes backgroundRes: Int) {
        getView<View>(viewId).setBackgroundResource(backgroundRes)
    }

    open fun setVisible(@IdRes viewId: Int, isVisible: Boolean) {
        val view = getView<View>(viewId)
        view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    open fun setGone(@IdRes viewId: Int, isGone: Boolean) {
        val view = getView<View>(viewId)
        view.visibility = if (isGone) View.GONE else View.VISIBLE
    }

    open fun setEnabled(@IdRes viewId: Int, isEnabled: Boolean) {
        getView<View>(viewId).isEnabled = isEnabled
    }
}