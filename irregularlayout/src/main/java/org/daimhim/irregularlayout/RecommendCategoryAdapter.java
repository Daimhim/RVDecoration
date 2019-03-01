package org.daimhim.irregularlayout;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;


import org.daimhim.pictureload.ImgLoadingUtil;
import org.daimhim.rvadapter.RecyclerContract;
import org.daimhim.rvadapter.RecyclerViewClick;
import org.daimhim.rvadapter.RecyclerViewExpandable;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * 项目名称：net.meyki.skt.ui.newhome
 * 项目版本：v2.8.0
 * 创建时间：2019/2/28 15:11  星期四
 * 创建人：Administrator
 * 修改时间：2019/2/28 15:11  星期四
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class RecommendCategoryAdapter extends RecyclerViewExpandable<RecyclerViewClick.ClickViewHolder,RecyclerViewClick.ClickViewHolder>
implements RecyclerContract.ExpandableContract<List<ItemListBean>,ItemListBean, DataListBean> {
    private List<ItemListBean> mItemListBeans;

    public RecommendCategoryAdapter() {
        mItemListBeans = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return mItemListBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getChild(groupPosition).getDataList().size();
    }

    @Override
    public RecyclerViewClick.ClickViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewClick.ClickViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_recommend_category_tile,parent,false));
    }

    @Override
    public RecyclerViewClick.ClickViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewClick.ClickViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_home_one_layout,parent,false));
    }

    @Override
    public void onBindGroupViewHolder(RecyclerViewClick.ClickViewHolder holder, int groupPosition) {
        holder.getTextView(R.id.tv_recommend_title).setText(getChild(groupPosition).getItemName()+"("+groupPosition+")");
    }

    @Override
    public void onBindChildViewHolder(RecyclerViewClick.ClickViewHolder holder, int groupPosition, int childPosition) {
        DataListBean lChildItem = getChildItem(groupPosition, childPosition);
        ImgLoadingUtil.ImageLoadConfig lDefaultConfig = ImgLoadingUtil.getDefaultConfig();
        lDefaultConfig.setPlaysholder(R.drawable.ic_launcher_background);
        ImageView lImageView = holder.getImageView(R.id.iv_goods_img);
        ViewGroup.LayoutParams lLayoutParams = lImageView.getLayoutParams();
        int lItemWidth = getChildItem(groupPosition, childPosition).getItemWidth();
        switch (lItemWidth){
            case 12:
                lLayoutParams.height = SystemUtil.dip2px(lImageView.getContext(),173f);
                break;
            case 6:
                lLayoutParams.height = SystemUtil.dip2px(lImageView.getContext(),163f);
                break;
            case 4:
                lLayoutParams.height = SystemUtil.dip2px(lImageView.getContext(),92f);
                break;
            case 3:
                lLayoutParams.height = SystemUtil.dip2px(lImageView.getContext(),83f);
                break;
        }
        lImageView.setLayoutParams(lLayoutParams);
//        ImgLoadingUtil.loadImage(lDefaultConfig, lImageView,lChildItem.getGoodsImg());
        holder.getTextView(R.id.tv_goods_name).setText(lChildItem.getGoodsName());
        holder.getTextView(R.id.tv_goods_price).setText("¥"+"("+groupPosition+")"+"("+childPosition+")");
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return getChildItem(groupPosition, childPosition).getItemWidth();
    }

    @Override
    protected int getSpanSize(int defSize, int position) {
        Pair<Integer, Integer> lIntegerIntegerPair = indexOfPosition(position);
        int lSpanSize = defSize;
        if (lIntegerIntegerPair.second == -1){
            lSpanSize = super.getSpanSize(defSize, position);
        }else {
            DataListBean lChildItem = getChildItem(lIntegerIntegerPair.first, lIntegerIntegerPair.second);
            lSpanSize = lChildItem.getItemWidth();
        }
        return lSpanSize;
    }

    @Override
    public void onRefresh(List<ItemListBean> ts) {
        mItemListBeans.clear();
        mItemListBeans.addAll(ts);
        notifyDataSetChanged();
    }

    @Override
    public void onLoad(List<ItemListBean> ts, int groupPosition, int position) {

    }

    @Override
    public DataListBean getChildItem(int groupPosition, int childPosition) {
        return mItemListBeans.get(groupPosition).getDataList().get(childPosition);
    }

    @Override
    public ItemListBean getChild(int groupPosition) {
        return mItemListBeans.get(groupPosition);
    }
}
