package rv.daimhim.rvdecorationtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.daimhim.rvadapter.RecyclerContract;
import org.daimhim.rvadapter.RecyclerViewClick;
import org.daimhim.rvadapter.RecyclerViewEmpty;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：rv.daimhim.rvdecorationtest
 * 项目版本：RVDecoration
 * 创建时间：2018.01.26 14:42
 * 修改人：Daimhim
 * 修改时间：2018.01.26 14:42
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */

public class LinearLayoutAdapter extends RecyclerViewClick<LinearLayoutAdapter.LinearLayoutViewholder>
        implements RecyclerContract.SimpleContract<List<String>,String>{
    private List<String> mStrings;

    public LinearLayoutAdapter() {
        mStrings = new ArrayList<>();
    }

    @Override
    public LinearLayoutViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearLayoutViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(LinearLayoutViewholder holder, int position) {
        holder.mTvContent.setText(getItem(position));
        holder.performItemClick(holder.mTvContent,this,position);
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

    @Override
    public void onLoad(List<String> strings) {

    }

    class LinearLayoutViewholder extends RecyclerViewEmpty.ClickViewHolder {
        @BindView(R.id.tv_content)
        TextView mTvContent;
        public LinearLayoutViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
