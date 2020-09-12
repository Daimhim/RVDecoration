package org.daimhim.rvadapter;

import android.util.Pair;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;


/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/12/28 10:03
 * 修改人：Daimhim
 * 修改时间：2017/12/28 10:03
 * 类描述：
 * 修改备注：
 * @author Daimhim
 */

public abstract class RecyclerViewExpandable<VHG extends RecyclerViewEmpty.EmptyViewHolder, VHC extends RecyclerViewEmpty.EmptyViewHolder>
        extends RecyclerViewEmpty<RecyclerViewEmpty.EmptyViewHolder> implements RecyclerContract.Expandable {
    private RecyclerContract.OnGroupItemClickListener mOnGroupItemClickListeners;
    private RecyclerContract.OnGroupItemLongClickListener mOnGroupItemLongClickListener;

    private RecyclerContract.OnChildItemClickListener mOnChildItemClickListeners;
    private RecyclerContract.OnChildItemLongClickListener mOnChildItemLongClickListener;

    private ExpandableHelper mExpandableHelper = new ExpandableHelper(this);

    public ExpandableHelper getExpandableHelper() {
        return mExpandableHelper;
    }

    @Override
    public final RecyclerViewEmpty.EmptyViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        if (viewType > 0) {
            return onCreateChildViewHolder(parent, viewType);
        } else {
            return onCreateGroupViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindDataViewHolder(RecyclerViewEmpty.EmptyViewHolder holder, int position) {
        int lViewType = getItemViewType(position);
        Pair<Integer, Integer> integerPair = mExpandableHelper.indexOfPosition(position);
        if (holder == null) {return;}
        if (lViewType > 0) {
            onBindChildViewHolder((VHC) holder, integerPair.first, integerPair.second);
        } else {
            onBindGroupViewHolder((VHG) holder, integerPair.first);
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
    public int getDataItemViewType(int position) {
        Pair<Integer, Integer> integerPair = mExpandableHelper.indexOfPosition(position);
        if (integerPair.second == -1) {
            return checkParameters(-getGroupItemViewType(integerPair.first));
        } else {
            return checkParameters(getChildItemViewType(integerPair.first, integerPair.second));
        }
    }

    @Override
    public long getItemId(int position) {
        Pair<Integer, Integer> integerPair = mExpandableHelper.indexOfPosition(position);
        if (getItemViewType(position) > 0) {
            return getChildItemId(integerPair.first, integerPair.second);
        } else {
            return getGroupItemId(integerPair.first);
        }
    }

    /**
     * 获取Id
     * @param groupPosition Group位置
     * @return ID
     */
    public long getGroupItemId(int groupPosition) {
        return -1;
    }

    /**
     * 获取Id
     * @param groupPosition Group位置
     * @param childPosition Child位置
     * @return ID
     */
    public long getChildItemId(int groupPosition, int childPosition) {
        return -1;
    }

    /**
     * 获取视图类型
     *
     * @param groupPosition Group Position
     * @return Type Value
     */
    public int getGroupItemViewType(int groupPosition) {
        return 1;
    }

    /**
     * 获取视图类型
     * @param groupPosition Group Position
     * @param childPosition Child Position
     * @return Type Value
     */
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 1;
    }

    /**
     * 获取Group视图数量
     *
     * @return total
     */
    @Override
    public abstract int getGroupCount();

    /**
     * 获取Child视图数量
     * @param groupPosition Group Position
     * @return total
     */
    @Override
    public abstract int getChildrenCount(int groupPosition);

    /**
     * 创建视图
     *
     * @param parent 父布局
     * @param viewType 视图类型
     * @return 视图
     */
    public abstract VHG onCreateGroupViewHolder(ViewGroup parent, int viewType);

    /**
     * 创建 Child View
     * @param parent Group View
     * @param viewType View Type Value
     * @return Child View
     */
    public abstract VHC onCreateChildViewHolder(ViewGroup parent, int viewType);

    /**
     * 设置数据
     *
     * @param holder View
     * @param groupPosition Group Position
     */
    public abstract void onBindGroupViewHolder(VHG holder, int groupPosition);

    /**
     * set Data
     * @param holder View
     * @param groupPosition Group position
     * @param childPosition Chile position
     */
    public abstract void onBindChildViewHolder(VHC holder, int groupPosition, int childPosition);

    @Override
    public void onBindEmptyViewHolder(RecyclerViewEmpty.EmptyViewHolder holder, int position) {

    }

    /**
     * get Child click
     * @return child click
     */
    public RecyclerContract.OnChildItemClickListener getOnChildItemClickListeners() {
        return mOnChildItemClickListeners;
    }

    /**
     * 点击事件
     *
     * @param view View
     * @param position 位置
     */
    @Override
    public void onItemClick(View view, int position) {
        Pair<Integer, Integer> integerPair = mExpandableHelper.indexOfPosition(position);
        if (getItemViewType(position) > 0 && mOnChildItemClickListeners != null) {
            mOnChildItemClickListeners.onChildItemClick(view, integerPair.first, integerPair.second);
        } else if (getItemViewType(position) < 0 && mOnGroupItemClickListeners != null) {
            mOnGroupItemClickListeners.onGroupItemClick(view, integerPair.first);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        int ofValue = mExpandableHelper.indexOfValue(position);
        if (ofValue < 0 && null != mOnChildItemLongClickListener) {
            Pair<Integer, Integer> integerPair = mExpandableHelper.indexOfPosition(position);
            mOnChildItemLongClickListener.onChildItemLongClick(view, integerPair.first, integerPair.second);
        } else if (ofValue > 0 && null != mOnGroupItemLongClickListener) {
            mOnGroupItemLongClickListener.onGroupItemLongClick(view, ofValue);
        }
    }

    /**
     * set group click
     * @param onGroupItemClickListeners external obj
     */
    public void setOnGroupItemClickListeners(RecyclerContract.OnGroupItemClickListener onGroupItemClickListeners) {
        mOnGroupItemClickListeners = onGroupItemClickListeners;
    }

    /**
     * set group long click
     * @param onGroupItemLongClickListener external obj
     */
    public void setOnGroupItemLongClickListener(RecyclerContract.OnGroupItemLongClickListener onGroupItemLongClickListener) {
        mOnGroupItemLongClickListener = onGroupItemLongClickListener;
    }

    /**
     * set child click
     * @param onChildItemClickListeners external obj
     */
    public void setOnChildItemClickListeners(RecyclerContract.OnChildItemClickListener onChildItemClickListeners) {
        mOnChildItemClickListeners = onChildItemClickListeners;
    }

    /**
     * set child long click
     * @param onChildItemLongClickListener external obj
     */
    public void setOnChildItemLongClickListener(RecyclerContract.OnChildItemLongClickListener onChildItemLongClickListener) {
        mOnChildItemLongClickListener = onChildItemLongClickListener;
    }
    /**
     * get GroupItemClick
     * @return child click
     */
    public RecyclerContract.OnGroupItemClickListener getOnGroupItemClickListeners() {
        return mOnGroupItemClickListeners;
    }
    /**
     * get GroupItemLongClick
     * @return child click
     */
    public RecyclerContract.OnGroupItemLongClickListener getOnGroupItemLongClickListener() {
        return mOnGroupItemLongClickListener;
    }
    /**
     * get ChildItemLongClick
     * @return child click
     */
    public RecyclerContract.OnChildItemLongClickListener getOnChildItemLongClickListener() {
        return mOnChildItemLongClickListener;
    }

    /**
     * check View Type
     * @param viewType Vaue
     * @return value
     */
    public int checkParameters(int viewType){
        if (viewType == 0){
            throw new NullPointerException("viewType!=0!");
        }
        return viewType;
    }
}
