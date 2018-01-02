package rv.daimhim.rvdecorationtest.leveldecorationdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import rv.daimhim.rvdecorationtest.R;

/**
 * 项目名称：rv.daimhim.rvdecorationtest.leveldecorationdemo
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/12/25 10:54
 * 修改人：Daimhim
 * 修改时间：2017/12/25 10:54
 * 类描述：
 * 修改备注：
 */

public class LevelDecorationAdapter2  extends RecyclerViewClick<LevelDecorationAdapter2.LevelDecorationViewHolder2> implements RecyclerContract.SpecificationContract<List<String>, String>{
    Context mContext;
    List<String> mStrings;

    public LevelDecorationAdapter2(Context context) {
        mContext = context;
        mStrings = new ArrayList<>();
    }
    @Override
    public LevelDecorationViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LevelDecorationViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.item_level_decoration, parent, false));
    }

    @Override
    public void onBindViewHolder(LevelDecorationViewHolder2 holder, int position) {
        holder.mTvContent.setText(getItem(position));
        holder.performItemClick(holder.mTvContent,this);
        holder.performItemClick(holder.mTvAdd,this);
        holder.performItemClick(holder.mTvDelete,this);
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
        notifyItemRemoved(position);
        mStrings.remove(position);
        notifyItemRangeChanged(position,getItemCount());
    }

    @Override
    public void replaceItem(String s, int position) {

    }

    @Override
    public String getItem(int position) {
        return null;
    }

    public class LevelDecorationViewHolder2 extends RecyclerViewClick.ClickViewHolder{

        @BindView(R.id.tv_add)
        TextView mTvAdd;
        @BindView(R.id.tv_delete)
        TextView mTvDelete;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public LevelDecorationViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
