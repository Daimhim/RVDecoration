package org.daimhim.rvadapter;

import android.view.View;


/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：MeykiHelper-fp-v1.6.7
 * 创建人：Daimhim
 * 创建时间：2017/12/24 16:30
 * 修改人：Daimhim
 * 修改时间：2017/12/24 16:30
 * 类描述：
 * 修改备注：
 *
 * @author Daimhim
 */

public abstract class RecyclerViewClick<VH extends RecyclerViewClick.ClickViewHolder> extends android.support.v7.widget.RecyclerView.Adapter<VH> {
    /**
     * 日志
     */
    protected String TAG = String.format("TAG:%s",getClass().getSimpleName());
    /**
     * 点击事件
     */
    private RecyclerContract.OnItemClickListener mOnItemClickListener;
    /**
     * 长点击事件
     */
    private RecyclerContract.OnItemLongClickListener mOnItemLongClickListener;

    /**
     * set点击事件
     * @param onItemClickListener 点击事件监听对象
     */
    public void setOnItemClickListener(RecyclerContract.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 长点击事件
     * @param onItemLongClickListener 点击事件监听对象
     */
    public void setOnItemLongClickListener(RecyclerContract.OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 点击事件 执行过程
     * @param view 视图
     * @param position 位置
     */
    public void onItemClick(View view, int position){
        if (null!=mOnItemClickListener) {
            mOnItemClickListener.onItemClick(view, position);
        }
    }

    /**
     * Long Click process
     * @param view View
     * @param position 位置
     */
    public void onItemLongClick(View view, int position){
        if (null!=mOnItemLongClickListener) {
            mOnItemLongClickListener.onItemLongClick(view, position);
        }
    }

    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);
        holder.mOnClickListener = null;
        holder.mOnLongClickListener = null;
        holder.mRecyclerViewClick = null;
    }

    /**
     * 实现了点击事件
     */
    public static class ClickViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        View.OnClickListener mOnClickListener;
        View.OnLongClickListener mOnLongClickListener;
        RecyclerViewClick mRecyclerViewClick;
        public ClickViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 执行点击事件
         * @param view   需要设置点击事件的View
         * @param recyclerViewClick Adapter对象
         * @return 是否set成功
         */
        public boolean performItemClick(View view,RecyclerViewClick recyclerViewClick){
            //保证一个ViewHolder只有一个OnClickListener对象 通过getLayoutPosition（）
            if (mOnClickListener == null){
                mRecyclerViewClick = recyclerViewClick;
                mOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getLayoutPosition() < mRecyclerViewClick.getItemCount()
                                && getLayoutPosition() >= 0 && null!=mRecyclerViewClick) {
                            mRecyclerViewClick.onItemClick(v,getLayoutPosition());
                        }
                    }
                };
            }
            view.setOnClickListener(mOnClickListener);
            return true;
        }

        /**
         * 执行点击事件
         * @param view 需要设置点击事件的View
         * @param recyclerViewClick Adapter对象
         * @return is set success
         */
        public boolean performLongItemClick(View view,RecyclerViewClick recyclerViewClick){
            if (mOnLongClickListener == null){
                mRecyclerViewClick = recyclerViewClick;
                mOnLongClickListener = new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (getLayoutPosition() < mRecyclerViewClick.getItemCount()
                                && getLayoutPosition() >= 0 && null!=mRecyclerViewClick) {
                            mRecyclerViewClick.onItemLongClick(v, getLayoutPosition());
                        }
                        return false;
                    }
                };
            }
            view.setOnLongClickListener(mOnLongClickListener);
            return true;
        }
    }

}
