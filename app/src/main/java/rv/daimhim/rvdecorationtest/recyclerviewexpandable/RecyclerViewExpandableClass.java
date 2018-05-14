package rv.daimhim.rvdecorationtest.recyclerviewexpandable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rv.daimhim.rvdecorationtest.R;

/**
 * 项目名称：rv.daimhim.rvdecorationtest
 * 项目版本：RVDecoration
 * 创建时间：2018.05.14 10:45
 * 修改人：Daimhim
 * 修改时间：2018.05.14 10:45
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */
public class RecyclerViewExpandableClass extends AppCompatActivity implements OnRefreshListener {
    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.srl_smartrefreshlayout)
    SmartRefreshLayout mSrlSmartrefreshlayout;
    private RecyclerViewExpandableAdapter mRecyclerViewExpandableAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_expandableclass);
        ButterKnife.bind(this);
        mRecyclerViewExpandableAdapter = new RecyclerViewExpandableAdapter(this);
        mSrlSmartrefreshlayout.setOnRefreshListener(this);
        mRvRecyclerview.setAdapter(mRecyclerViewExpandableAdapter);
        SimpleArrayMap<String, List<String>> lArrayMap = new SimpleArrayMap<>();
        ArrayList<String> lStrings = null;
        for (int i = 0; i < 10; i++) {
            lStrings = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                lStrings.add(String.valueOf(j));
            }
            lArrayMap.put(String.valueOf(i), lStrings);
        }
        mRecyclerViewExpandableAdapter.onRefresh(lArrayMap);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        SimpleArrayMap<String, List<String>> lArrayMap = new SimpleArrayMap<>();
        ArrayList<String> lStrings = null;
        for (int i = 0; i < 10; i++) {
            lStrings = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                lStrings.add(String.valueOf(j));
            }
            lArrayMap.put(String.valueOf(i), lStrings);
        }
        mRecyclerViewExpandableAdapter.onRefresh(lArrayMap);
        refreshLayout.finishRefresh();
    }
}
