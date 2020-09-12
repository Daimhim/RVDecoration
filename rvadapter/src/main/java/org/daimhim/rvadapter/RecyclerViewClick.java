package org.daimhim.rvadapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
public abstract class RecyclerViewClick<VH extends RecyclerViewClick.ClickViewHolder> extends RecyclerView.Adapter<VH> {
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
    public void setOnItemLongClickListener(RecyclerContract.OnItemLongClickListener pOnItemLongClickListener) {
        this.pOnItemLongClickListener = pOnItemLongClickListener;
    }

    /**
     * 点击事件 执行过程
     *
     * @param view     视图
     * @param position 位置
     */
    public void onItemClick(View view, int position) {
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
    public static class ClickViewHolder extends RecyclerView.ViewHolder {
        RecyclerContract.RecyclerClickListener mRecyclerClickListener;
        RecyclerContract.RecyclerLongClickListener mRecyclerLongClickListener;

        public ClickViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 执行点击事件
         *
         * @param view              需要设置点击事件的View
         * @param recyclerViewClick Adapter对象
         * @return 是否set成功
         */
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
        public boolean performLongItemClick(View view, RecyclerViewClick recyclerViewClick) {
            if (mRecyclerLongClickListener == null) {
                mRecyclerLongClickListener = new RecyclerContract.RecyclerLongClickListener();
            }
            mRecyclerLongClickListener.setPositionRecyclerView(recyclerViewClick, getAdapterPosition());
            view.setOnLongClickListener(mRecyclerLongClickListener);
            return true;
        }

    }

}
