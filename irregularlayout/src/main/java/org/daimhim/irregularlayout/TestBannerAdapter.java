package org.daimhim.irregularlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.daimhim.banner.Banner;
import org.daimhim.banner.loader.ImageLoader;
import org.daimhim.rvadapter.RecyclerContract;
import org.daimhim.rvadapter.RecyclerViewEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：org.daimhim.irregularlayout
 * 项目版本：RVDecoration
 * 创建时间：2019/3/5 09:00  星期二
 * 创建人：Administrator
 * 修改时间：2019/3/5 09:00  星期二
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class TestBannerAdapter extends RecyclerViewEmpty<RecyclerViewEmpty.ClickViewHolder>
implements RecyclerContract.SimpleContract<List<String>,String> {
    private List<String> mStrings;

    public TestBannerAdapter() {
        mStrings = new ArrayList<>();
    }

    @Override
    public ClickViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View lInflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_test_banner, parent, false);
        Banner lBanner = lInflate.findViewById(R.id.vp_banner);
        lBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                if (path instanceof String){
                    Glide.with(context).load(path).into(imageView);
                }
            }

            @Override
            public ImageView createImageView(Context context) {
                ViewGroup.MarginLayoutParams lLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                ImageView lImageView = new ImageView(context);
                lImageView.setLayoutParams(lLayoutParams);
                return lImageView;
            }
        });
        return new ClickViewHolder(lInflate);
    }

    @Override
    public void onBindDataViewHolder(ClickViewHolder holder, int position) {
        Banner lBanner = (Banner) holder.getView(R.id.vp_banner);
        lBanner.update(mStrings);
    }

    @Override
    public int getDataItemCount() {
        return 1;
    }

    @Override
    public void onRefresh(List<String> pStrings) {
        mStrings.clear();
        mStrings.addAll(pStrings);
        notifyDataSetChanged();
    }

    @Override
    public int getDataItemViewType(int position) {
        return 11;
    }

    @Override
    public String getItem(int position) {
        return mStrings.get(position);
    }

    @Override
    public void onLoad(List<String> pStrings) {

    }
}
