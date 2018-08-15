package org.daimhim.rvadapter;


import android.view.ViewGroup;

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

public class AdapterManagement extends RecyclerViewEmpty<RecyclerViewEmpty.ClickViewHolder>
        implements RecyclerContract.SimpleContract<ArrayList<RecyclerViewEmpty<RecyclerViewEmpty.ClickViewHolder>>, RecyclerViewEmpty> {

    /**
     * adapter manage
     */
    private ArrayList<RecyclerViewEmpty<ClickViewHolder>> mRecyclerViewClicks;
    /**
     * adapter 订阅
     */
    private ArrayList<AdapterManagementDataObserver> mHomeAdapterDataObservers;
    /**
     * 当前位置
     */
    private int mCurrentPosition = -1;

    public AdapterManagement() {
        mRecyclerViewClicks = new ArrayList<>();
        mHomeAdapterDataObservers = new ArrayList<>();
    }


    @Override
    public ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        android.util.Pair<Integer, Integer> integerIntegerPair = indexOfPosition(mCurrentPosition);
        return mRecyclerViewClicks.get(integerIntegerPair.first).onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindDataViewHolder(ClickViewHolder holder, int position) {
        android.util.Pair<Integer, Integer> integerIntegerPair = indexOfPosition(position);
        mRecyclerViewClicks.get(integerIntegerPair.first).onBindViewHolder(holder, integerIntegerPair.second);
    }

    @Override
    public int getDataItemCount() {
        int total = 0;
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            total += mRecyclerViewClicks.get(i).getItemCount();
        }
        return total;
    }

    @Override
    public int getDataItemViewType(int position) {
        mCurrentPosition = position;
        android.util.Pair<Integer, Integer> integerIntegerPair = indexOfPosition(position);
        return mRecyclerViewClicks.get(integerIntegerPair.first).getItemViewType(integerIntegerPair.second);

    }

    /**
     * 添加adapter
     *
     * @param recyclerViewClick adapter
     */
    public void addAdapter(RecyclerViewEmpty recyclerViewClick) {
        recyclerViewClick.setBaseCount(getItemCount());
        mRecyclerViewClicks.add(recyclerViewClick);
        notifyItemRangeInserted(getItemCount(),recyclerViewClick.getItemCount());
        modifyBase();
    }

    /**
     * 移除
     *
     * @param position adapter
     */
    public void removeAdapter(int position) {
        mRecyclerViewClicks.remove(position);
        modifyBase();
    }

    /**
     * 在数量发生变化时 更新每个adapter在manager中的位置
     */
    public void modifyBase(){
        int num = 0;
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            mRecyclerViewClicks.get(i).setBaseCount(num);
            num += mRecyclerViewClicks.get(i).getItemCount();
        }
    }

    /**
     * 查找
     *
     * @param recyclerViewClick 被查找对象
     * @return 位置 或者 -1
     */
    public int indexOf(RecyclerViewEmpty recyclerViewClick) {
        return mRecyclerViewClicks.indexOf(recyclerViewClick);
    }

    /**
     * 移除
     *
     * @param recyclerViewClick adapter
     */
    public void removeAdapter(RecyclerViewEmpty recyclerViewClick) {
        mRecyclerViewClicks.remove(recyclerViewClick);
        modifyBase();
    }
    /**
     * 逆序 通过一维向量 查找二维数组
     *
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
        AdapterManagementDataObserver homeAdapterDataObserver = null;
        int total = 0;
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            total += mRecyclerViewClicks.get(i).getItemCount();
            homeAdapterDataObserver = new AdapterManagementDataObserver(this, total);
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

    @Override
    public void onRefresh(ArrayList<RecyclerViewEmpty<ClickViewHolder>> pRecyclerViewEmpties) {
        mRecyclerViewClicks.clear();
        mRecyclerViewClicks.addAll(pRecyclerViewEmpties);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewEmpty<ClickViewHolder> getItem(int position) {
        return mRecyclerViewClicks.get(position);
    }

    @Override
    public void onLoad(ArrayList<RecyclerViewEmpty<ClickViewHolder>> pRecyclerViewEmpties) {
        mRecyclerViewClicks.addAll(pRecyclerViewEmpties);
        notifyDataSetChanged();
    }


    /**
     * 私有订阅
     */
    static class AdapterManagementDataObserver extends android.support.v7.widget.RecyclerView.AdapterDataObserver {
        private RecyclerViewEmpty mRecyclerViewClick;
        /**
         * 当前adapter的起始位置
         */
        private int mPositionStart = -1;

        public AdapterManagementDataObserver(RecyclerViewEmpty recyclerViewClick, int position) {
            mRecyclerViewClick = recyclerViewClick;
            mPositionStart = position;
        }

        @Override
        public void onChanged() {
            mRecyclerViewClick.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeChanged(mPositionStart + positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mRecyclerViewClick.notifyItemRangeChanged(mPositionStart + positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeInserted(mPositionStart + positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeRemoved(mPositionStart + positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mRecyclerViewClick.notifyItemMoved(mPositionStart + fromPosition, mPositionStart + toPosition);
        }
    }
}
