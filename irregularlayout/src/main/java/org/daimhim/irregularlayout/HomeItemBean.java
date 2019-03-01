package org.daimhim.irregularlayout;

import java.util.List;

/**
 * 项目版本：v2.8.0
 * 创建时间：2019/2/28 15:04  星期四
 * 创建人：Administrator
 * 修改时间：2019/2/28 15:04  星期四
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class HomeItemBean {
    private String apiHttpDomain;
    private List<ItemListBean> itemList;
    private String imgDomain;

    public String getApiHttpDomain() {
        return apiHttpDomain;
    }

    public void setApiHttpDomain(String pApiHttpDomain) {
        apiHttpDomain = pApiHttpDomain;
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> pItemList) {
        itemList = pItemList;
    }

    public String getImgDomain() {
        return imgDomain;
    }

    public void setImgDomain(String pImgDomain) {
        imgDomain = pImgDomain;
    }

    @Override
    public String toString() {
        return "HomeItemBean{" +
                "apiHttpDomain='" + apiHttpDomain + '\'' +
                ", itemList=" + itemList +
                ", imgDomain='" + imgDomain + '\'' +
                '}';
    }
}
