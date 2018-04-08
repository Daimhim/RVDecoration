package rv.daimhim.rvdecorationtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.daimhim.rvadapter.RecyclerContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.RecycleDecoration;

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

public class LinearLayoutActivity extends AppCompatActivity implements OnRefreshListener, RecyclerContract.OnItemClickListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.srl_smartrefreshlayout)
    SmartRefreshLayout mSrlSmartrefreshlayout;
    LinearLayoutAdapter mLinearLayoutAdapter;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        setContentView(R.layout.activity_level_decoration);
        mContext = this;
        ButterKnife.bind(this);
        mSrlSmartrefreshlayout.setOnRefreshListener(this);
        initView();
    }

    private void initView() {
        mLinearLayoutAdapter = new LinearLayoutAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRvRecyclerview.setLayoutManager(linearLayoutManager);
        mRvRecyclerview.setAdapter(mLinearLayoutAdapter);
        mLinearLayoutAdapter.onRefresh(initData(50));
        mLinearLayoutAdapter.setOnItemClickListener(this);
        RecycleDecoration lRecycleDecoration = new DecorationBuilder.Builder(mRvRecyclerview)
                .linearlayout(R.color.colorPrimary, R.dimen.dimen_size_2).create();
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
    public void onItemClick(View view, int position) {
        Toast.makeText(mContext,"position:"+position,Toast.LENGTH_SHORT).show();
    }
}
