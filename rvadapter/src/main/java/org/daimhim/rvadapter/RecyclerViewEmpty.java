package org.daimhim.rvadapter;

import android.view.ViewGroup;

/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/12/25 13:58
 * 修改人：Daimhim
 * 修改时间：2017/12/25 13:58
 * 类描述：
 * 修改备注：
 *
 * @author Daimhim
 */

public abstract class RecyclerViewEmpty<VH extends RecyclerViewClick.ClickViewHolder> extends RecyclerViewClick<VH> implements RecyclerContract.EmptyContract<VH> {

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmptyView(parent, viewType)) {
            return onCreateEmptyViewHolder(parent, viewType);
        } else {
            return onCreateDataViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        if (isEmptyView(holder, position)) {
            onBindEmptyViewHolder(holder, position);
        } else {
            onBindDataViewHolder(holder, position);
        }
    }

    /**
     * 获取Item数量 可能会有头部和尾部 所以加上数据长度以便扩充
     * 而是否空页面的决定在于isEmptyView
     *
     * @return
     */
    @Override
    public final int getItemCount() {
        if (isEmptyView()) {
            return 1 + getDataItemCount();
        } else {
            return getDataItemCount();
        }
    }

    /**
     * 是否空界面
     *
     * @return
     */
    @Override
    public boolean isEmptyView() {
        return getDataItemCount() == 0;
    }

    public boolean isEmptyView(VH holder, int position) {
        return isEmptyView();
    }

    public boolean isEmptyView(ViewGroup parent, int viewType) {
        return isEmptyView();
    }

    /**
     * 空页面加载数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindEmptyViewHolder(RecyclerViewClick.ClickViewHolder holder, int position) {

    }

    /**
     * 加载空页面
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public VH onCreateEmptyViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * 数据加载页面
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract VH onCreateDataViewHolder(ViewGroup parent, int viewType);

    /**
     * 数据加载页面
     *
     * @param holder
     * @param position
     */
    public abstract void onBindDataViewHolder(RecyclerViewClick.ClickViewHolder holder, int position);

    /**
     * 获取数据Item长度
     *
     * @return
     */
    public abstract int getDataItemCount();

}
