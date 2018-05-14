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

public abstract class RecyclerViewExpandable<VHG extends RecyclerViewClick.ClickViewHolder, VHC extends RecyclerViewClick.ClickViewHolder>
        extends RecyclerViewEmpty<RecyclerViewClick.ClickViewHolder> {
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
     * @param groupPosition Group位置
     */
    public final void notifyGroupPositionChanged(int groupPosition) {
        int num = 0;
        for (int i = 0; i < groupPosition; i++) {
            num += getChildrenCount(i);
            num++;
        }
        mSparseArray.put(getGroupCount(), num);
    }

    /**
     * 逆序 通过一维向量 查找二维数组
     * @param position 一维位置
     * @return 二维对象
     */
    public final Pair<Integer, Integer> indexOfPosition(int position) {
        int num = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            num += getChildrenCount(i);
            num++;
            if (num > position) {
                num -= getChildrenCount(i);
                num--;
                if (num == position) {
                    return new Pair<>(i, -1);
                }
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

    /**
     * 通过起始点查找 二维数组的起始位置
     * @param groupPosition  起始点
     * @return 二维数组对象
     */
    public final Pair<Integer, Integer> indexOfGroupPosition(int groupPosition) {
        int num = 0;
        for (int i = 0; i < groupPosition; i++) {
            num += getChildrenCount(i);
            num++;
        }
        return new Pair<>(groupPosition, num);
    }

    /**
     * 顺序 通过二维数组 查找一维向量
     * @param groupPosition 二维起始点
     * @param position 位置
     * @return 一维位置
     */
    public final int indexOfItemInPosition(int groupPosition, int position) {
        int num = 0;
        for (int i = 0; i < groupPosition - 1; i++) {
            num += getChildrenCount(i);
            num++;
        }
        return num + position;
    }


    @Override
    public final RecyclerViewClick.ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        if (viewType > 0) {
            return onCreateChildViewHolder(parent, viewType);
        } else {
            return onCreateGroupViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindDataViewHolder(RecyclerViewClick.ClickViewHolder holder, int position) {
        int lViewType = getItemViewType(position);
        Pair<Integer, Integer> integerPair = indexOfPosition(position);
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
        Pair<Integer, Integer> integerPair = indexOfPosition(position);
        if (integerPair.second == -1) {
            return checkParameters(-getGroupItemViewType(integerPair.first));
        } else {
            return checkParameters(getChildItemViewType(integerPair.first, integerPair.second));
        }
    }

    @Override
    public long getItemId(int position) {
        Pair<Integer, Integer> integerPair = indexOfPosition(position);
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
    public abstract int getGroupCount();

    /**
     * 获取Child视图数量
     * @param groupPosition Group Position
     * @return total
     */
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
    public void onBindEmptyViewHolder(ClickViewHolder holder, int position) {

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
        Pair<Integer, Integer> integerPair = indexOfPosition(position);
        if (getItemViewType(position) > 0 && mOnChildItemClickListeners != null) {
            mOnChildItemClickListeners.onChildItemClick(view, integerPair.first, integerPair.second);
        } else if (getItemViewType(position) < 0 && mOnGroupItemClickListeners != null) {
            mOnGroupItemClickListeners.onGroupItemClick(view, integerPair.first);
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
