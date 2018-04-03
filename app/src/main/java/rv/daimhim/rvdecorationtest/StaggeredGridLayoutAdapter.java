package rv.daimhim.rvdecorationtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.daimhim.rvadapter.RecyclerContract;
import org.daimhim.rvadapter.RecyclerViewClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：rv.daimhim.rvdecorationtest
 * 项目版本：RVDecoration
 * 创建时间：2018.01.26 19:31
 * 修改人：Daimhim
 * 修改时间：2018.01.26 19:31
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */

public class StaggeredGridLayoutAdapter extends RecyclerViewClick<StaggeredGridLayoutAdapter.StaggeredGridLayoutViewHolder>
        implements RecyclerContract.SimpleContract<List<String>, String> {
    private List<String> mStrings;

    public StaggeredGridLayoutAdapter() {
        mStrings = new ArrayList<>();
    }

    @Override
    public StaggeredGridLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StaggeredGridLayoutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.staggered_grid_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(StaggeredGridLayoutViewHolder holder, int position) {
        holder.mTvContent.setText(getItem(position));
        //手动更改高度，不同位置的高度有所不同
        holder.mTvContent.setHeight(100 + (position % 3) * 30);
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    @Override
    public void onRefresh(List<String> list) {
        mStrings.clear();
        mStrings.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public String getItem(int position) {
        return mStrings.get(position);
    }

    class StaggeredGridLayoutViewHolder extends RecyclerViewClick.ClickViewHolder {
        @BindView(R.id.tv_content)
        TextView mTvContent;
        public StaggeredGridLayoutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
