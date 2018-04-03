package rv.daimhim.rvdecorationtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：rv.daimhim.rvdecorationtest
 * 项目版本：RVDecoration
 * 创建时间：2018.01.26 14:37
 * 修改人：Daimhim
 * 修改时间：2018.01.26 14:37
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */

public class GridLayoutActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.srl_smartrefreshlayout)
    SmartRefreshLayout mSrlSmartrefreshlayout;
    GridLayoutAdapter mGridLayoutAdapter;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_decoration);
        mContext = this;
        ButterKnife.bind(this);
        mSrlSmartrefreshlayout.setOnRefreshListener(this);
        mSrlSmartrefreshlayout.setOnLoadMoreListener(this);
        initView();
    }

    private void initView() {
        mGridLayoutAdapter = new GridLayoutAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
        mRvRecyclerview.setLayoutManager(gridLayoutManager);
        mRvRecyclerview.setAdapter(mGridLayoutAdapter);
        mGridLayoutAdapter.onRefresh(initData(50));
    }

    private List<String> initData(int i) {
        List<String> stringList = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            stringList.add(j+"");
        }
        return stringList;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore();
    }
}