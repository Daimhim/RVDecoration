package rv.daimhim.rvdecorationtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.RecycleDecoration;

/**
 * 项目名称：rv.daimhim.rvdecorationtest
 * 项目版本：RVDecoration
 * 创建时间：2018.01.26 14:38
 * 修改人：Daimhim
 * 修改时间：2018.01.26 14:38
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */

public class StaggeredGridLayoutActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.srl_smartrefreshlayout)
    SmartRefreshLayout mSrlSmartrefreshlayout;
    StaggeredGridLayoutAdapter mStaggeredGridLayoutAdapter;
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
        mStaggeredGridLayoutAdapter = new StaggeredGridLayoutAdapter();
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRvRecyclerview.setLayoutManager(staggeredGridLayoutManager);
        mRvRecyclerview.setAdapter(mStaggeredGridLayoutAdapter);
        mStaggeredGridLayoutAdapter.onRefresh(initData(50));
        RecycleDecoration lRecycleDecoration = new DecorationBuilder.Builder(mRvRecyclerview).
                staggeredGridlayout(R.color.colorPrimary, R.dimen.dimen_size_2).create();
        mRvRecyclerview.addItemDecoration(lRecycleDecoration);
    }

    private List<String> initData(int i) {
        List<String> strings = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            strings.add(j+"");
        }
        return strings;
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
