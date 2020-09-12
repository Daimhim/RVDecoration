package org.daimhim.rvadapter;


/**
 * @Classname PreloadPagedListAdapter
 * @Description TODO
 * @Date 2019/6/28 16:41
 * @Created by Daimhim
 * Class description Daimhim太懒了什么都没有留下
 */
//public abstract class PreloadPagedListAdapter<T> extends RecyclerViewClick<RecyclerViewClick.ClickViewHolder>
//        implements RecyclerContract.EmptyContract {
//    private final AsyncPagedListDiffer<T> mDiffer;
//
//    public PreloadPagedListAdapter() {
//        this(false);
//    }
//
//    public PreloadPagedListAdapter(boolean isAsync) {
//        if (isAsync) {
//            mDiffer = new AsyncPagedListDiffer<T>(this,new DIFF_CALLBACK<T>());
//        } else {
//            mDiffer = new AsyncPagedListDiffer<T>(new AdapterListUpdateCallback(this),
//                    new AsyncDifferConfig.Builder<T>(new DIFF_CALLBACK<T>()).build());
//        }
//    }
//
//    @SuppressLint("RestrictedApi")
//    protected PagedList<T> getPagedList(DataSource<Integer, T> dataSource) {
//        return new PagedList.Builder<>(dataSource, getPagedListConfig())
//                .setInitialKey(getInitialKey())
//                .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
//                .setFetchExecutor(ArchTaskExecutor.getMainThreadExecutor())
//                .build();
//    }
//
//    protected PagedList.Config getPagedListConfig() {
//        return new PagedList.Config.Builder()
//                .setInitialLoadSizeHint(getPageSize() * 3)
//                .setPrefetchDistance(getPageSize())
//                .setPageSize(getPageSize())
//                .setEnablePlaceholders(true)
//                .build();
//    }
//
//    public Integer getInitialKey() {
//        return 1;
//    }
//
//    public int getPageSize() {
//        return 15;
//    }
//
//    public void submitList(PagedList<T> pagedList) {
//        mDiffer.submitList(pagedList);
//    }
//
//    @Override
//    public final int getItemViewType(int position) {
//        position = getVirtualLocation(position);
//        if (isEmptyView(position)) {
//            return getEmptyViewType();
//        } else {
//            return getDataItemViewType(position);
//        }
//    }
//
//    /**
//     * 在manager中使用
//     *
//     * @param position
//     * @return
//     */
//    protected int getVirtualLocation(int position) {
//        return position;
//    }
//
//    public void onCurrentListChanged(@Nullable PagedList<T> currentList) {
//
//    }
//
//    @NonNull
//    @Override
//    public ClickViewHolder onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {
//        if (isEmptyView(pViewGroup, pI)) {
//            return onCreateEmptyViewHolder(pViewGroup, pI);
//        } else {
//            return onCreateDataViewHolder(pViewGroup, pI);
//        }
//    }
//
//    @Override
//    public final void onBindViewHolder(RecyclerViewClick.ClickViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position);
//        position = getVirtualLocation(position);
//        if (isEmptyView(holder, position)) {
//            onBindEmptyViewHolder(holder, position);
//        } else {
//            onBindDataViewHolder(holder, position);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        if (isEmptyView()) {
//            return 1;
//        } else {
//            return getDataItemCount();
//        }
//    }
//
//    @Override
//    public boolean isEmptyView() {
//        return false;
//    }
//
//    @Override
//    public void onBindEmptyViewHolder(ClickViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public ClickViewHolder onCreateEmptyViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    public void onRefresh() {
//        DataSource<Integer, T> lDataSource = (DataSource<Integer, T>) mDiffer.getCurrentList().getDataSource();
//        mDiffer.submitList(null);
//        initData(lDataSource);
//    }
//
//    public void initData(DataSource<Integer, T> dataSource) {
//        submitList(getPagedList(dataSource));
//    }
//
//    public T getItem(int position) {
//        return mDiffer.getItem(position);
//    }
//
//
//    public boolean isEmptyView(int position) {
//        return isEmptyView();
//    }
//
//    public boolean isEmptyView(RecyclerViewClick.ClickViewHolder holder, int position) {
//        return isEmptyView();
//    }
//
//    public boolean isEmptyView(ViewGroup parent, int viewType) {
//        return isEmptyView();
//    }
//
//    /**
//     * 获取空界面Type
//     *
//     * @return 空界面Type
//     */
//    public int getEmptyViewType() {
//        return 0;
//    }
//
//    /**
//     * 获取布局ViewType
//     *
//     * @param position
//     * @return
//     */
//    public int getDataItemViewType(int position) {
//        return 0;
//    }
//
//    /**
//     * 数据加载页面
//     *
//     * @param parent   父布局
//     * @param viewType value
//     * @return View
//     */
//    public abstract RecyclerViewClick.ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType);
//
//    /**
//     * 数据加载页面
//     *
//     * @param holder   item
//     * @param position 位置
//     */
//    public abstract void onBindDataViewHolder(RecyclerViewClick.ClickViewHolder holder, int position);
//
//    /**
//     * 获取数据Item长度
//     *
//     * @return total
//     */
//    public int getDataItemCount() {
//        return mDiffer.getItemCount();
//    }
//
//    public static class DIFF_CALLBACK<T> extends DiffUtil.ItemCallback<T> {
//
//        @Override
//        public boolean areItemsTheSame(@NonNull Object pO, @NonNull Object pT1) {
//            return pO.equals(pT1);
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Object pO, @NonNull Object pT1) {
//            return pO.equals(pT1);
//        }
//
//        @Nullable
//        @Override
//        public Object getChangePayload(@NonNull T oldItem, @NonNull T newItem) {
//            return super.getChangePayload(oldItem, newItem);
//        }
//    }
//}
