package org.daimhim.rvadapter;

import android.annotation.SuppressLint;
import android.arch.core.executor.ArchTaskExecutor;
import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：RVDecoration
 * 创建时间：2019/1/17 10:27  星期四
 * 创建人：Administrator
 * 修改时间：2019/1/17 10:27  星期四
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public abstract class RecyclerViewPreloadAdapter<K, T, VH extends RecyclerViewEmpty.ClickViewHolder> extends RecyclerViewEmpty<VH> {
    private PreloadPagedListAdapter<K, T, VH> mKTVHPreloadPagedListAdapter = null;

    static class PreloadPagedListAdapter<K, T, VH extends RecyclerViewEmpty.ClickViewHolder> extends PagedListAdapter<T, VH> {
        private RecyclerViewPreloadAdapter<K, T, VH> mKTVHRecyclerViewPreloadAdapter;

        protected PreloadPagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback,
                                          RecyclerViewPreloadAdapter<K, T, VH> pKTVHRecyclerViewPreloadAdapter) {
            super(diffCallback);
            mKTVHRecyclerViewPreloadAdapter = pKTVHRecyclerViewPreloadAdapter;
        }

        protected PreloadPagedListAdapter(@NonNull AsyncDifferConfig<T> config,
                                          RecyclerViewPreloadAdapter<K, T, VH> pKTVHRecyclerViewPreloadAdapter) {
            super(config);
            mKTVHRecyclerViewPreloadAdapter = pKTVHRecyclerViewPreloadAdapter;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return mKTVHRecyclerViewPreloadAdapter.onCreateDataViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            mKTVHRecyclerViewPreloadAdapter.onBindViewHolder(holder, position);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
            mKTVHRecyclerViewPreloadAdapter.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public void onCurrentListChanged(@Nullable PagedList<T> currentList) {
            mKTVHRecyclerViewPreloadAdapter.onCurrentListChanged(currentList);
        }

        @Nullable
        @Override
        public T getItem(int position) {
            return super.getItem(position);
        }
    }


    public RecyclerViewPreloadAdapter(DataSource<K, T> dataSource, boolean isAsync) {
        if (isAsync) {
            mKTVHPreloadPagedListAdapter = new PreloadPagedListAdapter<>(new AsyncDifferConfig.Builder<T>(new DiffUtil.ItemCallback<T>() {
                @Override
                public boolean areItemsTheSame(T oldItem, T newItem) {
                    return RecyclerViewPreloadAdapter.this.areItemsTheSame(oldItem, newItem);
                }

                @Override
                public boolean areContentsTheSame(T oldItem, T newItem) {
                    return RecyclerViewPreloadAdapter.this.areContentsTheSame(oldItem, newItem);
                }
            }).build(), this);
        } else {
            mKTVHPreloadPagedListAdapter = new PreloadPagedListAdapter<>(new DiffUtil.ItemCallback<T>() {
                @Override
                public boolean areItemsTheSame(T oldItem, T newItem) {
                    return RecyclerViewPreloadAdapter.this.areItemsTheSame(oldItem, newItem);
                }

                @Override
                public boolean areContentsTheSame(T oldItem, T newItem) {
                    return RecyclerViewPreloadAdapter.this.areContentsTheSame(oldItem, newItem);
                }
            }, this);
        }
        mKTVHPreloadPagedListAdapter.submitList(getPagedList(dataSource));
    }

    @SuppressLint("RestrictedApi")
    protected PagedList<T> getPagedList(DataSource<K, T> dataSource) {
        return new PagedList.Builder<>(dataSource, getPagedListConfig())
                .setInitialKey(getInitialKey())
                .setBoundaryCallback(new PagedList.BoundaryCallback<T>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull T itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                    }
                })
                .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
                .setFetchExecutor(ArchTaskExecutor.getIOThreadExecutor())
                .build();
    }

    public K getInitialKey() {
        return null;
    }

    public int getPageSize() {
        return 15;
    }

    public PagedList.Config getPagedListConfig() {
        return new PagedList.Config.Builder()
                .setPageSize(getPageSize())
                .build();
    }

    public T getItem(int position) {
        return mKTVHPreloadPagedListAdapter.getItem(position);
    }

    @Override
    public abstract VH onCreateDataViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindDataViewHolder(VH holder, int position);

    @Override
    public int getDataItemCount() {
        return mKTVHPreloadPagedListAdapter.getItemCount();
    }

    @SuppressWarnings("WeakerAccess")
    public void onCurrentListChanged(@Nullable PagedList<T> currentList) {
    }

    public boolean areItemsTheSame(T oldItem, T newItem) {
        return false;
    }

    public boolean areContentsTheSame(T oldItem, T newItem) {
        return false;
    }
}
