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
 * 创建时间：2018.01.26 19:18
 * 修改人：Daimhim
 * 修改时间：2018.01.26 19:18
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */

public class GridLayoutAdapter extends RecyclerViewClick<GridLayoutAdapter.GridLayoutViewHolder> implements RecyclerContract.SimpleContract<List<String>, String> {
    List<String> mStrings;

    public GridLayoutAdapter() {
        mStrings = new ArrayList<>();
    }

    @Override
    public GridLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GridLayoutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(GridLayoutViewHolder holder, int position) {
        holder.mTvContent.setText(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    @Override
    public void onRefresh(List<String> strings) {
        mStrings.clear();
        mStrings.addAll(strings);
        notifyDataSetChanged();
    }

    @Override
    public String getItem(int position) {
        return mStrings.get(position);
    }

    @Override
    public void onLoad(List<String> strings) {

    }

    class GridLayoutViewHolder extends RecyclerViewClick.ClickViewHolder {
        @BindView(R.id.tv_content)
        TextView mTvContent;
        public GridLayoutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
