package org.daimhim.rvadapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;


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
abstract class RecyclerViewClick<VH extends SimpleViewHolder> extends RecyclerView.Adapter<VH> {
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
    public void onViewRecycled(@NotNull VH holder) {
        super.onViewRecycled(holder);
        holder.setMRecyclerClickListener(null);
        holder.setMRecyclerLongClickListener(null);
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
            lLayoutManager1.setSpanSizeLookup(new BaseSpanSizeLookup<VH>(this, lLayoutManager1.getSpanCount()));
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

    static class BaseSpanSizeLookup<VH extends SimpleViewHolder> extends GridLayoutManager.SpanSizeLookup {
        private final RecyclerViewClick<VH> mRecyclerViewClick;
        private int defSize = 0;

        public BaseSpanSizeLookup(RecyclerViewClick<VH> pBaseAdapter, int pDefSize) {
            mRecyclerViewClick = pBaseAdapter;
            defSize = pDefSize;
        }

        @Override
        public int getSpanSize(int position) {
            return mRecyclerViewClick.getSpanSize(defSize, position);
        }
    }

}
