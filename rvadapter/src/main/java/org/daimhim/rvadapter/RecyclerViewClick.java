package org.daimhim.rvadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
                        mRecyclerViewClick.onItemClick(v,getLayoutPosition());
                    }
                };
            }else {
                view.setOnClickListener(mOnClickListener);
            }
            return true;
        }

        public boolean performLongItemClick(@NonNull View view,@NonNull RecyclerViewClick recyclerViewClick){
            if (mOnLongClickListener == null){
                mRecyclerViewClick = recyclerViewClick;
                mOnLongClickListener = new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mRecyclerViewClick.onItemLongClick(v,getLayoutPosition());
                        return false;
                    }
                };
            }else {
                view.setOnLongClickListener(mOnLongClickListener);
            }
            return true;
        }
    }

}
