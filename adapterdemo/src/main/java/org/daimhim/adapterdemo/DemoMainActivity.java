package org.daimhim.adapterdemo;

import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.daimhim.rvadapter.RecyclerViewEmpty;
import org.daimhim.rvadapter.RecyclerViewPreloadAdapter;

import java.util.ArrayList;
import java.util.UUID;

import timber.log.Timber;


public class DemoMainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRvRecyclerview;
    private SwipeRefreshLayout mSrlRefresh;
    private RecyclerViewPreloadAdapter<Integer,UserBean> mPreloadAdapter;
    private DataSource<Integer, UserBean> mBeanDataSource  = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_main);
        initView();
        mBeanDataSource = new PageKeyedDataSource<Integer, UserBean>() {

            @Override
            public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, UserBean> callback) {
                Timber.i("loadInitial:%s",params.requestedLoadSize);
                final ArrayList<UserBean> lUserBeans = new ArrayList<>();
                UserBean lBean = null;
                for (int i = 0; i < params.requestedLoadSize; i++) {
                    lBean = new UserBean();
                    lBean.setName("loadInitial:Name:"+i);
                    lBean.setId(UUID.randomUUID().toString().replace("-",""));
                    lUserBeans.add(lBean);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(lUserBeans,0,2);
                    }
                }).start();
            }

            @Override
            public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, UserBean> callback) {
                Timber.i("loadBefore key:%s requestedLoadSize:%s", params.key,params.requestedLoadSize);
//                final ArrayList<UserBean> lUserBeans = new ArrayList<>();
//                UserBean lBean = null;
//                for (int i = params.key; i < params.requestedLoadSize+params.key; i++) {
//                    lBean = new UserBean();
//                    lBean.setName("loadAfter:Name:"+i);
//                    lBean.setId(UUID.randomUUID().toString().replace("-",""));
//                    lUserBeans.add(lBean);
//                    Timber.i("loadBefore:"+i);
//                }
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException ep) {
//                            ep.printStackTrace();
//                        }
//                        callback.onResult(lUserBeans,params.key-1);
////                    }
////                }).start();
            }

            @Override
            public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, UserBean> callback) {
                Timber.i("loadAfter key:%s requestedLoadSize:%s", params.key,params.requestedLoadSize);
                final ArrayList<UserBean> lUserBeans = new ArrayList<>();
                UserBean lBean = null;
                for (int i = params.requestedLoadSize*params.key; i < (params.requestedLoadSize*params.key)+params.requestedLoadSize; i++) {
                    lBean = new UserBean();
                    lBean.setName("loadAfter:Name:"+i);
                    lBean.setId(UUID.randomUUID().toString().replace("-",""));
                    lUserBeans.add(lBean);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ep) {
                            ep.printStackTrace();
                        }
                        callback.onResult(lUserBeans,params.key+1);
                    }
                }).start();
            }
        };
        mPreloadAdapter = new RecyclerViewPreloadAdapter<Integer, UserBean>(mBeanDataSource) {
            @Override
            public ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerViewEmpty.ClickViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_activity_main,parent,false));
            }

            @Override
            public boolean areItemsTheSame(UserBean oldItem, UserBean newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(UserBean oldItem, UserBean newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public void onBindDataViewHolder(ClickViewHolder holder, int position) {
                UserBean lItem = getItem(position);
                holder.getTextView(R.id.tv_content).setText(lItem.getName());
            }

            @Override
            public int getPageSize() {
                return 5;
            }

            @Override
            public Integer getInitialKey() {
                return 1;
            }
        };
        mRvRecyclerview.setAdapter(mPreloadAdapter);
        mRvRecyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initView() {
        mRvRecyclerview = (RecyclerView) findViewById(R.id.rv_recyclerview);
        mSrlRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        mSrlRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

        mSrlRefresh.setRefreshing(false);
    }
}
