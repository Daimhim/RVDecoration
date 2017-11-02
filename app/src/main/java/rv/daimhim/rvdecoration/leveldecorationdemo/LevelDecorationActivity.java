package rv.daimhim.rvdecoration.leveldecorationdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rv.daimhim.rvdecoration.R;
import rv.daimhim.rvdecoration.layoutmanager.CardLayoutManager;

/**
 * 项目名称：com.example.demo.leveldecorationdemo
 * 项目版本：usedlibrary
 * 创建人：Daimhim
 * 创建时间：2017/10/26 15:01
 * 修改人：Daimhim
 * 修改时间：2017/10/26 15:01
 * 类描述：
 * 修改备注：
 *
 * @author Daimhim
 */

@SuppressLint("Registered")
public class LevelDecorationActivity extends FragmentActivity {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.srl_smartrefreshlayout)
    SmartRefreshLayout mSrlSmartrefreshlayout;
    private LevelDecorationAdapter mDecorationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_decoration);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mRvRecyclerview.setLayoutManager(new CardLayoutManager());
        mSrlSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mDecorationAdapter.onRefresh(initData(20));
                refreshlayout.finishRefresh();
            }
        });
        mSrlSmartrefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mDecorationAdapter.onLoad(initData(20),mDecorationAdapter.getItemCount());
                refreshlayout.finishLoadmore();
            }
        });
        mDecorationAdapter = new LevelDecorationAdapter(this);
        mRvRecyclerview.setAdapter(mDecorationAdapter);
        mDecorationAdapter.onRefresh(initData(20));
    }

    private List<String> initData(int i) {
        List<String> list = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            list.add(j+"");
        }
        return list;
    }
}
