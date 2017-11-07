package rv.daimhim.rvdecorationtest.leveldecorationdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.used.baseadapter.RecyclerAdapterClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rv.daimhim.rvdecorationtest.R;

/**
 * 项目名称：com.example.demo.leveldecorationdemo
 * 项目版本：usedlibrary
 * 创建人：Daimhim
 * 创建时间：2017/10/26 15:22
 * 修改人：Daimhim
 * 修改时间：2017/10/26 15:22
 * 类描述：
 * 修改备注：
 *
 * @author Daimhim
 */

public class LevelDecorationAdapter extends RecyclerAdapterClick<LevelDecorationAdapter.LevelDecorationViewHolder> implements IAdapterOperationAble<List<String>, String> {
    Context mContext;
    List<String> mStrings;

    public LevelDecorationAdapter(Context context) {
        mContext = context;
        mStrings = new ArrayList<>();
    }

    @Override
    public LevelDecorationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LevelDecorationViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_level_decoration, parent, false));
    }

    @Override
    public void onBindViewHolder(LevelDecorationViewHolder holder, int position) {
        holder.mTvContent.setText(getItem(position));

        holder.mTvContent.setOnClickListener(holder.getOnHolderClickListene());
        holder.mTvAdd.setOnClickListener(holder.getOnHolderClickListene());
        holder.mTvDelete.setOnClickListener(holder.getOnHolderClickListene());
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
    public void onLoad(List<String> list, int position) {
        mStrings.addAll(position, list);
        notifyDataSetChanged();
    }

    @Override
    public void insertItem(String s, int position) {
        mStrings.add(position,s);
        notifyItemInserted(position);
    }

    @Override
    public void deleteItem(int position) {
        mStrings.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void replaceItem(String s, int position) {

    }

    @Override
    public String getItem(int position) {
        return mStrings.get(position);
    }

    class LevelDecorationViewHolder extends RecyclerAdapterClick.BaseViewHolder {
        @BindView(R.id.tv_add)
        TextView mTvAdd;
        @BindView(R.id.tv_delete)
        TextView mTvDelete;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public LevelDecorationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
