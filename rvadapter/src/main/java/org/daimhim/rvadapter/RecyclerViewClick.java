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
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(@NonNull OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(@NonNull OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
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
                        mRecyclerViewClick.mOnItemClickListener.onItemClick(v,getLayoutPosition());
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
                        mRecyclerViewClick.mOnItemLongClickListener.onItemLongClick(v,getLayoutPosition());
                        return false;
                    }
                };
            }else {
                view.setOnLongClickListener(mOnLongClickListener);
            }
            return true;
        }
    }

    /**
     * 提供给外部的 接口  传入此接口 监听点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 长点击事件
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    /**
     * Adapter规范
     * @param <Ts>
     * @param <T>
     */
    public interface RecyclerAdapterContract<Ts,T> {
        /**
         * 刷新
         * @param ts 数据类型
         */
        void onRefresh(Ts ts);

        /**
         * 添加多条
         * @param ts 数据类型
         */
        void onLoad(Ts ts, int position);

        /**
         * 插入
         * @param t  数据类型
         * @param position 位置
         *                 该方法可以和add合并
         */
        void insertItem(T t, int position);

        /**
         * 删除
         * @param position 位置
         */
        void deleteItem(int position);

        /**
         * 替换
         * @param t 数据类型
         * @param position
         */
        void replaceItem(T t, int position);

        /**
         * 获取数据
         * @param position  位置
         * @return 数据类型
         */
        T getItem(int position);
    }
}
