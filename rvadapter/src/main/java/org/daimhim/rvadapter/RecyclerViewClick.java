package org.daimhim.rvadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public abstract class RecyclerViewClick<VH extends RecyclerViewClick.ClickViewHolder> extends RecyclerView.Adapter<VH> {

    protected String TAG = String.format("TAG:%s",getClass().getSimpleName());

    private RecyclerContract.OnItemClickListener mOnItemClickListener;
    private RecyclerContract.OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(@NonNull RecyclerContract.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(@NonNull RecyclerContract.OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public void onItemClick(View view, int position){
        if (null!=mOnItemClickListener) {
            mOnItemClickListener.onItemClick(view, position);
        }
    }
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

    public static class ClickViewHolder extends RecyclerView.ViewHolder {
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
         * @return
         */
        public boolean performItemClick(@NonNull View view,@NonNull RecyclerViewClick recyclerViewClick){
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

        public boolean performLongItemClick(@NonNull View view,@NonNull RecyclerViewClick recyclerViewClick){
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
