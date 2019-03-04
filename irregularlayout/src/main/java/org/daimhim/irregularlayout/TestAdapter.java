package org.daimhim.irregularlayout;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.daimhim.rvadapter.RecyclerContract;
import org.daimhim.rvadapter.RecyclerViewEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：org.daimhim.irregularlayout
 * 项目版本：RVDecoration
 * 创建时间：2019/3/1 17:47  星期五
 * 创建人：Administrator
 * 修改时间：2019/3/1 17:47  星期五
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class TestAdapter extends RecyclerViewEmpty<RecyclerViewEmpty.ClickViewHolder>
implements RecyclerContract.SimpleContract<List<String>,String> {
    private ArrayList<String> mStrings;

    public TestAdapter() {
        mStrings = new ArrayList<>();
    }

    @Override
    public ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        return new ClickViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_test,parent,false));
    }

    @Override
    public void onBindDataViewHolder(ClickViewHolder holder, int position) {
        holder.getTextView(R.id.tv_text_test).setText(position+"");
    }

    @Override
    public int getDataItemCount() {
        return mStrings.size();
    }

    @Override
    protected int getSpanSize(int defSize, int position) {
        return 12;
    }

    @Override
    public void onRefresh(List<String> pStrings) {
        mStrings.clear();
        mStrings.addAll(pStrings);
        notifyDataSetChanged();
    }

    @Override
    public String getItem(int position) {
        return mStrings.get(position);
    }

    @Override
    public void onLoad(List<String> pStrings) {

    }
}
