package org.daimhim.rvadapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

/**
 * @Classname RecyclerPreloadAdapter
 * @Description TODO
 * @Date 2019/6/28 2:53
 * @Created by Daimhim
 * Class description Daimhim太懒了什么都没有留下
 */
public class RecyclerPreloadAdapter<T,K extends RecyclerViewEmpty.ClickViewHolder> extends PagedListAdapter<T,K> {
    protected RecyclerPreloadAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public K onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull K pK, int pI) {

    }
}
