package rv.daimhim.rvdecorationtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.daimhim.rvadapter.RecyclerViewClick;
import org.daimhim.rvadapter.RecyclerViewEmpty;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：rv.daimhim.rvdecorationtest
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/12/25 16:54
 * 修改人：Daimhim
 * 修改时间：2017/12/25 16:54
 * 类描述：
 * 修改备注：
 *
 * @author Daimhim
 */

public class EmptyActivity extends Activity {
    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.srl_smartrefreshlayout)
    SmartRefreshLayout mSrlSmartrefreshlayout;
    RecyclerViewEmpty mRecyclerViewEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_decoration);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {

        mSrlSmartrefreshlayout.setOnRefreshListener( new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
            }
        });
        mSrlSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }
        });
        mRecyclerViewEmpty = new RecyclerViewEmpty() {

            @Override
            public ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindDataViewHolder(ClickViewHolder holder, int position) {

            }

            @Override
            public int getDataItemCount() {
                return 0;
            }
        };
        mRvRecyclerview.setAdapter(mRecyclerViewEmpty);

    }
    private List<String> initData(String key, int i) {
        List<String> list = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            list.add(key + ":" + j);
        }
        return list;
    }

    class Test extends RecyclerViewEmpty<Test.TestViewHolder>{
        @Override
        public TestViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindDataViewHolder(TestViewHolder holder, int position) {

        }

        @Override
        public int getDataItemCount() {
            return 0;
        }

        class TestViewHolder extends RecyclerViewClick.ClickViewHolder{

            public TestViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
