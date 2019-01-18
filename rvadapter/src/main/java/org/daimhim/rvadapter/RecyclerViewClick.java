package org.daimhim.rvadapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：MeykiHelper-fp-v1.6.7
 * 创建人：Daimhim
 * 创建时间：2017/12/24 16:30
 * 修改人：Daimhim
 * 修改时间：2017/12/24 16:30
 * 类描述：
 * 修改备注：该类仅限制内部使用，请用RecyclerViewEmpty替换
 *
 * @author Daimhim
 */
@Deprecated
public abstract class RecyclerViewClick<VH extends RecyclerViewClick.ClickViewHolder> extends android.support.v7.widget.RecyclerView.Adapter<VH> {
    /**
     * 扩展属性，用于adapter manager
     */
    private int mBaseCount;

    /**
     * 日志
     */
    protected String TAG = String.format("TAG:%s", getClass().getSimpleName());
    /**
     * 点击事件
     */
    protected RecyclerContract.OnItemClickListener pOnItemClickListener;
    /**
     * 长点击事件
     */
    protected RecyclerContract.OnItemLongClickListener pOnItemLongClickListener;

    /**
     * set点击事件
     *
     * @param onItemClickListener 点击事件监听对象
     */
    public void setOnItemClickListener(RecyclerContract.OnItemClickListener onItemClickListener) {
        pOnItemClickListener = onItemClickListener;
    }

    /**
     * 长点击事件
     *
     * @param pOnItemLongClickListener 点击事件监听对象
     */
    public void setpOnItemLongClickListener(RecyclerContract.OnItemLongClickListener pOnItemLongClickListener) {
        this.pOnItemLongClickListener = pOnItemLongClickListener;
    }

    /**
     * 点击事件 执行过程
     *
     * @param view     视图
     * @param position 位置
     */
    public void onItemClick(View view, int position) {
        if (null != pOnItemClickListener) {
            pOnItemClickListener.onItemClick(this,view, position - getBaseCount());
        }
    }

    /**
     * Long Click process
     *
     * @param view     View
     * @param position 位置
     */
    public void onItemLongClick(View view, int position) {
        if (null != pOnItemLongClickListener) {
            pOnItemLongClickListener.onItemLongClick(this,view, position - getBaseCount());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Log.d("TAG:"+getClass().getName(),"position:"+position);
    }

    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);
        holder.mOnClickListener = null;
        holder.mOnLongClickListener = null;
    }

    /**
     * 获取在之前有多少个
     * @return 基数
     */
    public int getBaseCount() {
        return mBaseCount;
    }
    /**
     * set在之前有多少个
     * @return 基数
     */
    public void setBaseCount(int baseCount) {
        mBaseCount = baseCount;
    }
    /**
     * 实现了点击事件
     */
    public static class ClickViewHolder<T> extends android.support.v7.widget.RecyclerView.ViewHolder {
        RecyclerContract.RecyclerClickListener mOnClickListener;
        RecyclerContract.RecyclerLongClickListener mOnLongClickListener;
        private SparseArray<View> mViews;

        public ClickViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        /**
         * 执行点击事件
         *
         * @param view              需要设置点击事件的View
         * @param recyclerViewClick Adapter对象
         * @return 是否set成功
         */
        public boolean performItemClick(View view, RecyclerViewClick recyclerViewClick,int position) {
            //保证一个ViewHolder只有一个OnClickListener对象 通过getLayoutPosition（）
            if (mOnClickListener == null) {
                mOnClickListener = new RecyclerContract.RecyclerClickListener();
            }
            mOnClickListener.setPositionRecyclerView(recyclerViewClick,position);
            view.setOnClickListener(mOnClickListener);
            return true;
        }

        /**
         * 执行点击事件
         *
         * @param view              需要设置点击事件的View
         * @param recyclerViewClick Adapter对象
         * @return is set success
         */
        public boolean performLongItemClick(View view, RecyclerViewClick recyclerViewClick,int position) {
            if (mOnLongClickListener == null) {
                mOnLongClickListener = new RecyclerContract.RecyclerLongClickListener();
            }
            mOnLongClickListener.setPositionRecyclerView(recyclerViewClick,position);
            view.setOnLongClickListener(mOnLongClickListener);
            return true;
        }

        public void onRefresh(T t) {
        }

        @SuppressWarnings("unchecked")
        private <V extends View> V findViewById(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (V) view;
        }

        public View getView(int viewId) {
            return findViewById(viewId);
        }

        public TextView getTextView(int viewId) {
            return (TextView) getView(viewId);
        }

        public Button getButton(int viewId) {
            return (Button) getView(viewId);
        }

        public ImageView getImageView(int viewId) {
            return (ImageView) getView(viewId);
        }

        public ImageButton getImageButton(int viewId) {
            return (ImageButton) getView(viewId);
        }

        public EditText getEditText(int viewId) {
            return (EditText) getView(viewId);
        }

        public ClickViewHolder setText(int viewId, String value) {
            TextView view = findViewById(viewId);
            view.setText(value);
            return this;
        }

        public ClickViewHolder setBackground(int viewId, int resId) {
            View view = findViewById(viewId);
            view.setBackgroundResource(resId);
            return this;
        }

        public ClickViewHolder setClickListener(int viewId, View.OnClickListener listener) {
            View view = findViewById(viewId);
            view.setOnClickListener(listener);
            return this;
        }

    }

}
