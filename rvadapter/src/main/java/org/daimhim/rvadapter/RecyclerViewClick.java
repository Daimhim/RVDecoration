package org.daimhim.rvadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.SoftReference;


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
    @Deprecated
    public void setOnItemClickListener(RecyclerContract.OnItemClickListener onItemClickListener) {
        pOnItemClickListener = onItemClickListener;
    }

    /**
     * 长点击事件
     *
     * @param pOnItemLongClickListener 点击事件监听对象
     */
    @Deprecated
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
        position = position-getBaseCount();
        //兼容处理
        if (null != pOnItemClickListener) {
            pOnItemClickListener.onItemClick(view, position - getBaseCount());
        }
    }

    /**
     * Long Click process
     *
     * @param view     View
     * @param position 位置
     */
    public void onItemLongClick(View view, int position) {
        position = position-getBaseCount();
        //兼容处理
        if (null != pOnItemLongClickListener) {
            pOnItemLongClickListener.onItemLongClick(view, position - getBaseCount());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
//        Log.d("TAG:" + getClass().getName(), "position:" + position);
    }

    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);
        holder.mRecyclerClickListener = null;
        holder.mRecyclerLongClickListener = null;
    }

    /**
     * 获取在之前有多少个
     *
     * @return 基数
     */
    public int getBaseCount() {
        return mBaseCount;
    }

    /**
     * set在之前有多少个
     *
     * @return 基数
     */
    public void setBaseCount(int baseCount) {
        mBaseCount = baseCount;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager lLayoutManager = recyclerView.getLayoutManager();
        if (lLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager lLayoutManager1 = (GridLayoutManager) lLayoutManager;
            lLayoutManager1.setSpanSizeLookup(new BaseSpanSizeLookup(this, lLayoutManager1.getSpanCount()));
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        RecyclerView.LayoutManager lLayoutManager = recyclerView.getLayoutManager();
        if (lLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager lLayoutManager1 = (GridLayoutManager) lLayoutManager;
            lLayoutManager1.setSpanSizeLookup(null);
        }
    }

    protected int getSpanSize(int defSize, int position) {
        return defSize;
    }

    class BaseSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        private RecyclerViewClick mRecyclerViewClick;
        private int defSize = 0;

        public BaseSpanSizeLookup(RecyclerViewClick pBaseAdapter, int pDefSize) {
            mRecyclerViewClick = pBaseAdapter;
            defSize = pDefSize;
        }

        @Override
        public int getSpanSize(int position) {
            return mRecyclerViewClick.getSpanSize(defSize, position);
        }
    }

    /**
     * 实现了点击事件
     */
    public static class ClickViewHolder<T> extends android.support.v7.widget.RecyclerView.ViewHolder {
        RecyclerContract.RecyclerClickListener mRecyclerClickListener;
        RecyclerContract.RecyclerLongClickListener mRecyclerLongClickListener;
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
        @Deprecated
        public boolean performItemClick(View view, RecyclerViewClick recyclerViewClick) {
            //保证一个ViewHolder只有一个OnClickListener对象 通过getLayoutPosition（）
            if (mRecyclerClickListener == null) {
                mRecyclerClickListener = new RecyclerContract.RecyclerClickListener();
            }
            mRecyclerClickListener.setPositionRecyclerView(recyclerViewClick, getAdapterPosition());
            view.setOnClickListener(mRecyclerClickListener);
            return true;
        }

        /**
         * 执行点击事件
         *
         * @param view              需要设置点击事件的View
         * @param recyclerViewClick Adapter对象
         * @return is set success
         */
        @Deprecated
        public boolean performLongItemClick(View view, RecyclerViewClick recyclerViewClick) {
            if (mRecyclerLongClickListener == null) {
                mRecyclerLongClickListener = new RecyclerContract.RecyclerLongClickListener();
            }
            mRecyclerLongClickListener.setPositionRecyclerView(recyclerViewClick, getAdapterPosition());
            view.setOnLongClickListener(mRecyclerLongClickListener);
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
