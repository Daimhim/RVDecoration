package org.daimhim.rvadapter;


import java.util.ArrayList;

/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：RVDecoration
 * 创建时间：2018.04.19 18:11
 * 修改人：Daimhim
 * 修改时间：2018.04.19 18:11
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */

public class AdapterManagement extends RecyclerViewClick<RecyclerViewClick.ClickViewHolder> {
    /**
     * adapter manage
     */
    private ArrayList<RecyclerViewClick> mRecyclerViewClicks;
    /**
     * adapter 订阅
     */
    private ArrayList<HomeAdapterDataObserver> mHomeAdapterDataObservers;
    /**
     * 当前位置
     */
    private int mCurrentPosition = -1;
    public AdapterManagement() {
        mRecyclerViewClicks = new ArrayList<>();
        mHomeAdapterDataObservers = new ArrayList<>();
    }

    @Override
    public ClickViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        android.util.Pair<Integer, Integer> integerIntegerPair = indexOfPosition(mCurrentPosition);
        return (ClickViewHolder) mRecyclerViewClicks.get(integerIntegerPair.first).onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ClickViewHolder holder, int position) {
        android.util.Pair<Integer, Integer> integerIntegerPair = indexOfPosition(position);
        mRecyclerViewClicks.get(integerIntegerPair.first).onBindViewHolder(holder,integerIntegerPair.second);
    }

    /**
     * 应急
     * @param viewType 查找类型
     * @return 位置
     */
    private android.util.Pair<Integer, Integer> findViewType(int viewType){
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            for (int j = 0; j < mRecyclerViewClicks.get(i).getItemCount(); j++) {
                int itemViewType = mRecyclerViewClicks.get(i).getItemViewType(j);
                if (itemViewType > 0){
                    itemViewType += i;
                }else {
                    itemViewType += (0 - i);
                }
                if (viewType == itemViewType){
                    return new android.util.Pair<>(i,j);
                }
            }
        }
        return new android.util.Pair<>(-1,-1);
    }
    @Override
    public int getItemCount() {
        int total = 0;
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            total += mRecyclerViewClicks.get(i).getItemCount();
        }
        return total;
    }

    /**
     * 添加adapter
     * @param recyclerViewClick adapter
     */
    public void addAdapter(RecyclerViewClick recyclerViewClick){
        mRecyclerViewClicks.add(recyclerViewClick);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        mCurrentPosition = position;
        android.util.Pair<Integer, Integer> integerIntegerPair = indexOfPosition(position);
        int itemViewType = mRecyclerViewClicks.get(integerIntegerPair.first).getItemViewType(integerIntegerPair.second);
        if (itemViewType > 0){
            itemViewType += integerIntegerPair.first;
        }else {
            itemViewType += (0 - integerIntegerPair.first);
        }
        return itemViewType;
    }
    /**
     * 逆序 通过一维向量 查找二维数组
     * @param position 一维位置
     * @return 二维对象
     */
    public final android.util.Pair<Integer, Integer> indexOfPosition(int position) {
        int num = 0;
        int itemCount = -1;
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            itemCount = mRecyclerViewClicks.get(i).getItemCount();
            num += itemCount;
            if (num > position) {
                num -= itemCount;
                for (int j = 0; j < itemCount; j++) {
                    if (num == position) {
                        return new android.util.Pair<>(i, j);
                    }
                    num++;
                }
            }
        }
        return new android.util.Pair<>(-1, -1);
    }
    @Override
    public void registerAdapterDataObserver(android.support.v7.widget.RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        HomeAdapterDataObserver homeAdapterDataObserver = null;
        int total = 0;
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            total += mRecyclerViewClicks.get(i).getItemCount();
            homeAdapterDataObserver = new HomeAdapterDataObserver(this,total);
            mRecyclerViewClicks.get(i).registerAdapterDataObserver(homeAdapterDataObserver);
            mHomeAdapterDataObservers.add(homeAdapterDataObserver);
        }
    }

    @Override
    public void unregisterAdapterDataObserver(android.support.v7.widget.RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            mRecyclerViewClicks.get(i).unregisterAdapterDataObserver(mHomeAdapterDataObservers.get(i));
        }
    }

    /**
     * 私有订阅
     */
    static class HomeAdapterDataObserver extends android.support.v7.widget.RecyclerView.AdapterDataObserver{
        private RecyclerViewClick mRecyclerViewClick;
        private int mPositionStart = -1;
        public HomeAdapterDataObserver(RecyclerViewClick recyclerViewClick, int position) {
            mRecyclerViewClick = recyclerViewClick;
            mPositionStart = position;
        }

        @Override
        public void onChanged() {
            mRecyclerViewClick.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeChanged(mPositionStart+positionStart,itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mRecyclerViewClick.notifyItemRangeChanged(mPositionStart+positionStart,itemCount,payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeInserted(mPositionStart+positionStart,itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeRemoved(mPositionStart+positionStart,itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mRecyclerViewClick.notifyItemMoved(fromPosition, toPosition);
        }
    }
}
