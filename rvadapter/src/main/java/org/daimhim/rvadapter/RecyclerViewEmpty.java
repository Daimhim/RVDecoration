package org.daimhim.rvadapter;

import android.view.View;
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

public abstract class RecyclerViewEmpty<VH extends RecyclerViewClick.ClickViewHolder> extends RecyclerViewClick<RecyclerViewClick.ClickViewHolder> implements EmptyViewContract {

    private View mEmptyView = null;

    @Override
    public RecyclerViewClick.ClickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClickViewHolder(mEmptyView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewClick.ClickViewHolder holder, int position) {

    }

    @Override
    public final int getItemCount() {
        return getItemQuantity() == 0 && mEmptyView != null ? 1 : 0;
    }

    public abstract int getItemQuantity();

    @Override
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }



}
