package org.daimhim.rvadapter;


import android.util.Pair;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class AdapterManagement extends RecyclerViewEmpty<RecyclerViewEmpty.EmptyViewHolder>
        implements RecyclerContract.SimpleContract<ArrayList<RecyclerViewEmpty<RecyclerViewEmpty.EmptyViewHolder>>,
        RecyclerViewEmpty<RecyclerViewEmpty.EmptyViewHolder>> , RecyclerContract.Expandable{

    /**
     * adapter manage
     */
    private ArrayList<RecyclerViewEmpty<EmptyViewHolder>> mRecyclerViewClicks;
    /**
     * adapter 订阅
     */
    private ArrayList<AdapterManagementDataObserver> mHomeAdapterDataObservers;
    /**
     * 当前位置
     */
    private int mCurrentPosition = -1;
    private ExpandableHelper mExpandableHelper = new ExpandableHelper(this);

    public ExpandableHelper getExpandableHelper() {
        return mExpandableHelper;
    }

    public AdapterManagement() {
        mRecyclerViewClicks = new ArrayList<>();
        mHomeAdapterDataObservers = new ArrayList<>();
    }


    @Override
    public EmptyViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        android.util.Pair<Integer, Integer> integerIntegerPair = mExpandableHelper.indexOfPosition(mCurrentPosition);
        return mRecyclerViewClicks.get(integerIntegerPair.first).onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindDataViewHolder(EmptyViewHolder holder, int position) {
        android.util.Pair<Integer, Integer> integerIntegerPair = mExpandableHelper.indexOfPosition(position);
        mRecyclerViewClicks.get(integerIntegerPair.first).onBindViewHolder(holder, integerIntegerPair.second);
    }

    @Override
    public int getDataItemCount() {
        int total = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            total += getChildrenCount(i);
        }
        return total;
    }

    @Override
    public int getDataItemViewType(int position) {
        mCurrentPosition = position;
        android.util.Pair<Integer, Integer> integerIntegerPair = mExpandableHelper.indexOfPosition(position);
        return mRecyclerViewClicks.get(integerIntegerPair.first).getItemViewType(integerIntegerPair.second);

    }

    /**
     * 添加adapter
     *
     * @param recyclerViewClick adapter
     */
    public void addAdapter(RecyclerViewEmpty<RecyclerViewEmpty.EmptyViewHolder> recyclerViewClick) {
        recyclerViewClick.setBaseCount(getItemCount());
        mRecyclerViewClicks.add(recyclerViewClick);
        notifyItemRangeInserted(getItemCount(),recyclerViewClick.getItemCount());
        modifyBase();
    }

    @Override
    protected int getSpanSize(int defSize, int position) {
        Pair<Integer, Integer> lIntegerIntegerPair = mExpandableHelper.indexOfPosition(position);
        return mRecyclerViewClicks.get(lIntegerIntegerPair.first).getSpanSize(defSize,lIntegerIntegerPair.second);
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
        for (int i = 0; i < getGroupCount(); i++) {
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
    public int indexOf(RecyclerViewEmpty<RecyclerViewEmpty.EmptyViewHolder> recyclerViewClick) {
        return mRecyclerViewClicks.indexOf(recyclerViewClick);
    }

    /**
     * 移除
     *
     * @param recyclerViewClick adapter
     */
    public void removeAdapter(RecyclerViewEmpty<RecyclerViewEmpty.EmptyViewHolder> recyclerViewClick) {
        mRecyclerViewClicks.remove(recyclerViewClick);
        modifyBase();
    }

    AdapterManagementDataObserver homeAdapterDataObserver = null;
    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);

        int total = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            total += getChildrenCount(i);
            homeAdapterDataObserver = new AdapterManagementDataObserver(this, total);
            mRecyclerViewClicks.get(i).registerAdapterDataObserver(homeAdapterDataObserver);
            mHomeAdapterDataObservers.add(homeAdapterDataObserver);
        }
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        for (int i = getGroupCount()-1; i >= 0; i--) {
            mRecyclerViewClicks.get(i).unregisterAdapterDataObserver(mHomeAdapterDataObservers.get(i));
            mHomeAdapterDataObservers.remove(i);
        }
    }

    @Override
    public void onRefresh(ArrayList<RecyclerViewEmpty<EmptyViewHolder>> pRecyclerViewEmpties) {
        mRecyclerViewClicks.clear();
        mRecyclerViewClicks.addAll(pRecyclerViewEmpties);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewEmpty<EmptyViewHolder> getItem(int position) {
        return mRecyclerViewClicks.get(position);
    }

    @Override
    public void onLoad(ArrayList<RecyclerViewEmpty<EmptyViewHolder>> pRecyclerViewEmpties) {
        mRecyclerViewClicks.addAll(pRecyclerViewEmpties);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mRecyclerViewClicks.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mRecyclerViewClicks.get(groupPosition).getItemCount();
    }


    /**
     * 私有订阅
     */
    static class AdapterManagementDataObserver extends RecyclerView.AdapterDataObserver {
        private AdapterManagement mRecyclerViewClick;
        /**
         * 当前adapter的起始位置
         */
        private int mPositionStart = -1;

        public AdapterManagementDataObserver(AdapterManagement recyclerViewClick, int position) {
            mRecyclerViewClick = recyclerViewClick;
            mPositionStart = position;
        }

        @Override
        public void onChanged() {
            mRecyclerViewClick.notifyDataSetChanged();
            mRecyclerViewClick.modifyBase();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeChanged(mPositionStart + positionStart, itemCount);
            mRecyclerViewClick.modifyBase();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mRecyclerViewClick.notifyItemRangeChanged(mPositionStart + positionStart, itemCount, payload);
            mRecyclerViewClick.modifyBase();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeInserted(mPositionStart + positionStart, itemCount);
            mRecyclerViewClick.modifyBase();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mRecyclerViewClick.notifyItemRangeRemoved(mPositionStart + positionStart, itemCount);
            mRecyclerViewClick.modifyBase();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mRecyclerViewClick.notifyItemMoved(mPositionStart + fromPosition, mPositionStart + toPosition);
            mRecyclerViewClick.modifyBase();
        }
    }
}
