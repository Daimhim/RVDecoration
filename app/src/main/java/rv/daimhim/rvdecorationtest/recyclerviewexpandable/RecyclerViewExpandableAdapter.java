package rv.daimhim.rvdecorationtest.recyclerviewexpandable;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.daimhim.rvadapter.RecyclerContract;
import org.daimhim.rvadapter.RecyclerViewClick;
import org.daimhim.rvadapter.RecyclerViewExpandable;

import java.util.List;

import rv.daimhim.rvdecorationtest.R;

/**
 * 项目名称：rv.daimhim.rvdecorationtest.recyclerviewexpandable
 * 项目版本：RVDecoration
 * 创建时间：2018.05.14 10:47
 * 修改人：Daimhim
 * 修改时间：2018.05.14 10:47
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */
public class RecyclerViewExpandableAdapter extends RecyclerViewExpandable<RecyclerViewExpandableAdapter.GroupViewHolder,RecyclerViewExpandableAdapter.ChildViewHolder>
implements RecyclerContract.ExpandableContract<SimpleArrayMap<String,List<String>>,List<String>,String>{
    SimpleArrayMap<String,List<String>> mArrayMap;
    Context mContext;

    public RecyclerViewExpandableAdapter(Context pContext) {
        mContext = pContext;
        mArrayMap = new SimpleArrayMap<>();
        notifyPositionChanged();
    }

    @Override
    public int getGroupCount() {
        return mArrayMap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mArrayMap.valueAt(groupPosition).size();
    }

    @Override
    public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new GroupViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_groupviewholder,parent,false));
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new ChildViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_childviewholder,parent,false));
    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int groupPosition, int childPosition) {

    }

    @Override
    public void onRefresh(SimpleArrayMap<String, List<String>> ts) {
        mArrayMap.clear();
        mArrayMap.putAll(ts);
        notifyPositionChanged();
        notifyDataSetChanged();
    }

    @Override
    public void onLoad(SimpleArrayMap<String, List<String>> ts, int groupPosition, int position) {

    }

    @Override
    public String getChildItem(int groupPosition, int childPosition) {
        return mArrayMap.valueAt(groupPosition).get(childPosition);
    }

    @Override
    public List<String> getChild(int groupPosition) {
        return mArrayMap.valueAt(groupPosition);
    }

    static class GroupViewHolder extends RecyclerViewClick.ClickViewHolder{

        public GroupViewHolder(View itemView) {
            super(itemView);
        }
    }
    static class ChildViewHolder extends RecyclerViewClick.ClickViewHolder{
        public ChildViewHolder(View itemView) {
            super(itemView);
        }
    }
}
