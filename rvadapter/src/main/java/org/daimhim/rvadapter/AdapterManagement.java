package org.daimhim.rvadapter;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
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

public class AdapterManagement extends RecyclerViewClick<RecyclerViewClick.ClickViewHolder> {
    private ArrayList<RecyclerViewClick> mRecyclerViewClicks;
    private ArrayList<HomeAdapterDataObserver> mHomeAdapterDataObservers;
    private int mCurrentPosition = -1;
    public AdapterManagement() {
        mRecyclerViewClicks = new ArrayList<>();
        mHomeAdapterDataObservers = new ArrayList<>();
    }

    @Override
    public ClickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Pair<Integer, Integer> integerIntegerPair = findViewType(viewType);
        return (ClickViewHolder) mRecyclerViewClicks.get(integerIntegerPair.first).onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ClickViewHolder holder, int position) {
        Pair<Integer, Integer> integerIntegerPair = indexOfPosition(position);
        mRecyclerViewClicks.get(integerIntegerPair.first).onBindViewHolder(holder,integerIntegerPair.second);
    }
    private Pair<Integer, Integer> findViewType(int viewType){
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            for (int j = 0; j < mRecyclerViewClicks.get(i).getItemCount(); j++) {
                int itemViewType = mRecyclerViewClicks.get(i).getItemViewType(j);
                if (itemViewType > 0){
                    itemViewType += i;
                }else {
                    itemViewType += (0 - i);
                }
                if (viewType == itemViewType){
                    return new Pair<>(i,j);
                }
            }
        }
        return new Pair<>(-1,-1);
    }
    @Override
    public int getItemCount() {
        int total = 0;
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            total += mRecyclerViewClicks.get(i).getItemCount();
        }
        return total;
    }
    public void addAdapter(RecyclerViewClick recyclerViewClick){
        mRecyclerViewClicks.add(recyclerViewClick);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        Pair<Integer, Integer> integerIntegerPair = indexOfPosition(position);
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
    public final Pair<Integer, Integer> indexOfPosition(int position) {
        int num = 0;
        int itemCount = -1;
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            itemCount = mRecyclerViewClicks.get(i).getItemCount();
            num += itemCount;
            if (num > position) {
                num -= itemCount;
                for (int j = 0; j < itemCount; j++) {
                    if (num == position) {
                        return new Pair<>(i, j);
                    }
                    num++;
                }
            }
        }
        return new Pair<>(-1, -1);
    }
    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
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
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        for (int i = 0; i < mRecyclerViewClicks.size(); i++) {
            mRecyclerViewClicks.get(i).unregisterAdapterDataObserver(mHomeAdapterDataObservers.get(i));
        }
    }

    static class HomeAdapterDataObserver extends RecyclerView.AdapterDataObserver{
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
