package org.daimhim.rvadapter;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.ArraySet;
import android.view.ViewGroup;

/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：RVDecoration
 * 创建时间：2018.04.16 16:38
 * 修改人：Daimhim
 * 修改时间：2018.04.16 16:38
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */

public class RecyclerViewAdapterCluster extends RecyclerViewClick<RecyclerViewClick.ClickViewHolder>{
    private ArraySet<RecyclerViewClick> mRecyclerViewClicks = new ArraySet<>();

    @Override
    public ClickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ClickViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        int total = 0;
        for (RecyclerViewClick item :
                mRecyclerViewClicks) {
            total += item.getItemCount();
        }
        return total;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
