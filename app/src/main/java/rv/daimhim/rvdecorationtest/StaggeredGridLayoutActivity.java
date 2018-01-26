package rv.daimhim.rvdecorationtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

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

public class StaggeredGridLayoutActivity extends FragmentActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.srl_smartrefreshlayout)
    SmartRefreshLayout mSrlSmartrefreshlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_decoration);
        ButterKnife.bind(this);
        mSrlSmartrefreshlayout.setOnRefreshListener(this);
        mSrlSmartrefreshlayout.setOnLoadmoreListener(this);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore();
    }
}
