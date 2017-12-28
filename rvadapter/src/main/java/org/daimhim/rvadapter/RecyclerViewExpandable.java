package org.daimhim.rvadapter;

import android.util.Pair;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import static android.support.v7.widget.RecyclerView.NO_ID;

/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/12/28 10:03
 * 修改人：Daimhim
 * 修改时间：2017/12/28 10:03
 * 类描述：
 * 修改备注：
 */

public abstract class RecyclerViewExpandable<VHG extends RecyclerViewClick.ClickViewHolder, VHC extends RecyclerViewClick.ClickViewHolder> extends RecyclerViewEmpty<RecyclerViewClick.ClickViewHolder> {
    private RecyclerContract.OnGroupItemClickListener mOnGroupItemClickListeners;
    private RecyclerContract.OnGroupItemLongClickListener mOnGroupItemLongClickListener;

    private RecyclerContract.OnChildItemClickListener mOnChildItemClickListeners;
    private RecyclerContract.OnChildItemLongClickListener mOnChildItemLongClickListener;

    /**
     * Key groupPosition
     * Value group>Position
     */
    private SparseIntArray mSparseArray;

    /**
     * 在数据改变之后调用
     */
    public final void notifyPositionChanged() {
        if (null == mSparseArray) {
            mSparseArray = new SparseIntArray();
        } else {
            mSparseArray.clear();
        }
        int num = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            mSparseArray.put(i, num);
            num += getChildrenCount(i);
            num++;
        }
        mSparseArray.put(getGroupCount(), num);
    }

    /**
     * 在数据改变之后调用
     */
    public final void notifyGroupPositionChanged(int groupPosition) {
        int num = 0;
        for (int i = 0; i < groupPosition; i++) {
            num += getChildrenCount(i);
            num++;
        }
        mSparseArray.put(getGroupCount(), num);
    }

    public final Pair<Integer, Integer> indexOfPosition(int position) {
        int num = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            num += getChildrenCount(i);
            num++;
            if (num > position) {
                num -= getChildrenCount(i);
                num--;
                for (int j = 0; j < getChildrenCount(i); j++) {
                    num++;
                    if (num == position) {
                        return new Pair<>(i, j);
                    }
                }
            }
        }
        return new Pair<>(-1, -1);
    }

    public final Pair<Integer, Integer> indexOfGroupPosition(int groupPosition) {
        int num = 0;
        for (int i = 0; i < groupPosition; i++) {
            num += getChildrenCount(i);
            num++;
        }
        return new Pair<>(groupPosition, num);
    }

    public final int indexOfItemInPosition(int groupPosition, int position) {
        int num = 0;
        for (int i = 0; i < groupPosition - 1; i++) {
            num += getChildrenCount(i);
            num++;
        }
        return num + position;
    }


    @Override
    public final ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0) {
            return onCreateGroupViewHolder(parent, viewType);
        } else {
            return onCreateChildViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindDataViewHolder(ClickViewHolder holder, int position) {
        int ofValue = mSparseArray.indexOfValue(position);
        if (ofValue < 0) {
            Pair<Integer, Integer> integerPair = indexOfPosition(position);
            onBindChildViewHolder((VHC) holder, integerPair.first, integerPair.second);
        } else {
            onBindGroupViewHolder((VHG) holder, ofValue);
        }
    }

    @Override
    public final int getDataItemCount() {
        int num = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            num += getChildrenCount(i);
        }
        return num + getGroupCount();
    }

    @Override
    public final int getItemViewType(int position) {
        int ofValue = mSparseArray.indexOfValue(position);
        if (ofValue < 0) {
            Pair<Integer, Integer> integerPair = indexOfPosition(position);
            return getChildItemViewType(integerPair.first, integerPair.second);
        } else {
            return -getGroupItemViewType(ofValue);
        }
    }

    @Override
    public final long getItemId(int position) {
        int ofValue = mSparseArray.indexOfValue(position);
        if (ofValue < 0) {
            Pair<Integer, Integer> integerPair = indexOfPosition(position);
            return getChildItemId(integerPair.first, integerPair.second);
        } else {
            return getGroupItemId(ofValue);
        }
    }

    /**
     * 获取ID
     *
     * @param groupPosition
     * @return
     */
    public long getGroupItemId(int groupPosition) {
        return NO_ID;
    }

    public long getChildItemId(int groupPosition, int childPosition) {
        return NO_ID;
    }

    /**
     * 获取视图类型
     *
     * @param groupPosition
     * @return
     */
    public int getGroupItemViewType(int groupPosition) {
        return 1;
    }

    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 1;
    }

    /**
     * 获取视图数量
     *
     * @return
     */
    public abstract int getGroupCount();

    public abstract int getChildrenCount(int groupPosition);

    /**
     * 创建视图
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract ClickViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType);

    public abstract ClickViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType);

    /**
     * 设置数据
     *
     * @param holder
     * @param groupPosition
     * @return
     */
    public abstract ClickViewHolder onBindGroupViewHolder(ClickViewHolder holder, int groupPosition);

    public abstract ClickViewHolder onBindChildViewHolder(ClickViewHolder holder, int groupPosition, int childPosition);

    /**
     * 空页面
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public abstract ClickViewHolder onCreateEmptyViewHolder(ViewGroup parent, int viewType);

    public RecyclerContract.OnChildItemClickListener getOnChildItemClickListeners() {
        return mOnChildItemClickListeners;
    }

    /**
     * 点击事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        int ofValue = mSparseArray.indexOfValue(position);
        if (ofValue < 0 && mOnChildItemClickListeners != null) {
            Pair<Integer, Integer> integerPair = indexOfPosition(position);
            mOnChildItemClickListeners.onChildItemClick(view, integerPair.first, integerPair.second);
        } else if (ofValue > 0 && mOnGroupItemClickListeners != null) {
            mOnGroupItemClickListeners.onGroupItemClick(view, ofValue);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        int ofValue = mSparseArray.indexOfValue(position);
        if (ofValue < 0 && null != mOnChildItemLongClickListener) {
            Pair<Integer, Integer> integerPair = indexOfPosition(position);
            mOnChildItemLongClickListener.onChildItemLongClick(view, integerPair.first, integerPair.second);
        } else if (ofValue > 0 && null != mOnGroupItemLongClickListener) {
            mOnGroupItemLongClickListener.onGroupItemLongClick(view, ofValue);
        }
    }

    public void setOnGroupItemClickListeners(RecyclerContract.OnGroupItemClickListener onGroupItemClickListeners) {
        mOnGroupItemClickListeners = onGroupItemClickListeners;
    }

    public void setOnGroupItemLongClickListener(RecyclerContract.OnGroupItemLongClickListener onGroupItemLongClickListener) {
        mOnGroupItemLongClickListener = onGroupItemLongClickListener;
    }

    public void setOnChildItemClickListeners(RecyclerContract.OnChildItemClickListener onChildItemClickListeners) {
        mOnChildItemClickListeners = onChildItemClickListeners;
    }

    public void setOnChildItemLongClickListener(RecyclerContract.OnChildItemLongClickListener onChildItemLongClickListener) {
        mOnChildItemLongClickListener = onChildItemLongClickListener;
    }
}
