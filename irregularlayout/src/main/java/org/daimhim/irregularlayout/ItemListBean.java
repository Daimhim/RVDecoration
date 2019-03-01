package org.daimhim.irregularlayout;

import java.util.List;

/**
 * 项目版本：v2.8.0
 * 创建时间：2019/2/28 15:05  星期四
 * 创建人：Administrator
 * 修改时间：2019/2/28 15:05  星期四
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class ItemListBean {
    private List<DataListBean> dataList;
    private String goodsId;
    private String imgUrl;
    private String itemId;
    private String itemName;
    private String itemNum;
    private String status;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> pDataList) {
        dataList = pDataList;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String pGoodsId) {
        goodsId = pGoodsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String pImgUrl) {
        imgUrl = pImgUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String pItemId) {
        itemId = pItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String pItemName) {
        itemName = pItemName;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String pItemNum) {
        itemNum = pItemNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String pStatus) {
        status = pStatus;
    }

    @Override
    public String toString() {
        return "ItemListBean{" +
                "dataList=" + dataList +
                ", goodsId='" + goodsId + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
