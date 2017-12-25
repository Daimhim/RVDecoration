package rv.daimhim.rvdecorationtest.leveldecorationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.daimhim.rvadapter.RecyclerViewClick;
import org.daimhim.rvadapter.RecyclerViewEmpty;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rv.daimhim.rvdecoration.DecorationBuilder2;
import rv.daimhim.rvdecorationtest.R;

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

public class LevelDecorationActivity extends Activity implements RecyclerViewClick.OnItemClickListener{

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.srl_smartrefreshlayout)
    SmartRefreshLayout mSrlSmartrefreshlayout;
    private LevelDecorationAdapter2 mDecorationAdapter;
    RecyclerViewEmpty mRecyclerViewEmpty;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_decoration);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

//        mSrlSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                mDecorationAdapter.onRefresh(initData("刷新", 20));
//                refreshlayout.finishRefresh();
//            }
//        });
//        mSrlSmartrefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                mDecorationAdapter.onLoad(initData("加载", 20), mDecorationAdapter.getItemCount());
//                refreshlayout.finishLoadmore();
//            }
//        });
//        mDecorationAdapter = new LevelDecorationAdapter2(this);
//        mDecorationAdapter.setOnItemClickListener(this);
//        mRvRecyclerview.setAdapter(mDecorationAdapter);
////        mRvRecyclerview.addItemDecoration(
////                new DecorationBuilder2(mRvRecyclerview)
////                .builder());
//        mRvRecyclerview.addItemDecoration(new DecorationBuilder2(mRvRecyclerview,5,R.color.cl_333333).builder());
//        mRvRecyclerview.setItemAnimator(new DefaultItemAnimator());
//        mDecorationAdapter.onRefresh(initData("初始化", 20));

        mSrlSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
            }
        });
        mSrlSmartrefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
            }
        });
        mRecyclerViewEmpty = new RecyclerViewEmpty() {
            @Override
            public int getItemQuantity() {
                return 0;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }
        };
        mRecyclerViewEmpty.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty,mRvRecyclerview,false));
        mRvRecyclerview.setAdapter(mRecyclerViewEmpty);
    }

    private List<String> initData(String key, int i) {
        List<String> list = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            list.add(key + ":" + j);
        }
        return list;
    }

    @Override
    public void onItemClick(View view, int i) {
        switch (view.getId()) {
            case R.id.tv_add:
                mDecorationAdapter.insertItem("插入："+(i+1),i);
                break;
            case R.id.tv_delete:
                mDecorationAdapter.deleteItem(i);
                break;
            case R.id.tv_content:
                mRvRecyclerview.invalidateItemDecorations();
                break;
            default:
                break;
        }
    }
}
