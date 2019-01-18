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
import android.support.v7.widget.RecyclerView;
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
public abstract class RecyclerViewPreloadAdapter<K, T> extends RecyclerViewEmpty<RecyclerViewEmpty.ClickViewHolder> {
    private DataSource<K, T> mDataSource;
    private PreloadPagedListAdapter<K, T> mKTVHPreloadPagedListAdapter = null;

    static class PreloadPagedListAdapter<K, T> extends PagedListAdapter<T, RecyclerViewEmpty.ClickViewHolder> {
        private RecyclerViewPreloadAdapter<K, T> mKTVHRecyclerViewPreloadAdapter;


        protected PreloadPagedListAdapter(final RecyclerViewPreloadAdapter<K, T> pKTVHRecyclerViewPreloadAdapter) {
            super(new AsyncDifferConfig.Builder<T>(new DiffUtil.ItemCallback<T>() {
                @Override
                public boolean areItemsTheSame(T oldItem, T newItem) {
                    return pKTVHRecyclerViewPreloadAdapter.areItemsTheSame(oldItem, newItem);
                }

                @Override
                public boolean areContentsTheSame(T oldItem, T newItem) {
                    return pKTVHRecyclerViewPreloadAdapter.areContentsTheSame(oldItem, newItem);
                }

                @Override
                public Object getChangePayload(T oldItem, T newItem) {
                    return pKTVHRecyclerViewPreloadAdapter.getChangePayload(oldItem, newItem);
                }
            }).build());
            mKTVHRecyclerViewPreloadAdapter = pKTVHRecyclerViewPreloadAdapter;
            registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    mKTVHRecyclerViewPreloadAdapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    mKTVHRecyclerViewPreloadAdapter.notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                    mKTVHRecyclerViewPreloadAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    mKTVHRecyclerViewPreloadAdapter.notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    mKTVHRecyclerViewPreloadAdapter.notifyItemRangeRemoved(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    mKTVHRecyclerViewPreloadAdapter.notifyItemMoved(fromPosition, toPosition);
                }
            });
        }

        @NonNull
        @Override
        public RecyclerViewEmpty.ClickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return mKTVHRecyclerViewPreloadAdapter.onCreateDataViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewEmpty.ClickViewHolder holder, int position) {
            mKTVHRecyclerViewPreloadAdapter.onBindViewHolder(holder, position);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewEmpty.ClickViewHolder holder, int position, @NonNull List<Object> payloads) {
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


    public RecyclerViewPreloadAdapter(DataSource<K, T> dataSource) {
        mDataSource = dataSource;
        mKTVHPreloadPagedListAdapter = new PreloadPagedListAdapter<>(this);
        mKTVHPreloadPagedListAdapter.submitList(getPagedList(dataSource));
    }

    @SuppressLint("RestrictedApi")
    protected PagedList<T> getPagedList(DataSource<K, T> dataSource) {
        return new PagedList.Builder<>(dataSource, getPagedListConfig())
                .setInitialKey(getInitialKey())
                .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
                .setFetchExecutor(ArchTaskExecutor.getIOThreadExecutor())
                .build();
    }

    public void onRefresh() {
        mKTVHPreloadPagedListAdapter.submitList(null);
        mKTVHPreloadPagedListAdapter.submitList(getPagedList(mDataSource));
    }


    @Override
    public void onViewRecycled(ClickViewHolder holder) {
        super.onViewRecycled(holder);
        mDataSource = null;
        mKTVHPreloadPagedListAdapter = null;
    }

    public K getInitialKey() {
        return null;
    }

    public int getPageSize() {
        return 15;
    }

    public PagedList.Config getPagedListConfig() {
        return new PagedList.Config.Builder()
                .setInitialLoadSizeHint(getPageSize()*3)
                .setPrefetchDistance(getPageSize())
                .setPageSize(getPageSize())
                .setEnablePlaceholders(true)
                .build();
    }

    public T getItem(int position) {
        return mKTVHPreloadPagedListAdapter.getItem(position);
    }

    @Override
    public abstract RecyclerViewEmpty.ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindDataViewHolder(RecyclerViewEmpty.ClickViewHolder holder, int position);

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

    public Object getChangePayload(T oldItem, T newItem) {
        return null;
    }
}
