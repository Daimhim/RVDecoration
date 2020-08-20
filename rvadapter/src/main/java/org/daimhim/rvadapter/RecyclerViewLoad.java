package org.daimhim.rvadapter;

import android.view.ViewGroup;

public class RecyclerViewLoad extends RecyclerViewEmpty<RecyclerViewEmpty.ClickViewHolder> {
    private RecyclerContract.OnLoadListener mOnLoadListener;
    @Override
    public ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindDataViewHolder(ClickViewHolder holder, int position) {

    }

    @Override
    public int getDataItemCount() {
        return 0;
    }

    public void setOnLoadListener(RecyclerContract.OnLoadListener pOnLoadListener) {
        mOnLoadListener = pOnLoadListener;
    }
}
