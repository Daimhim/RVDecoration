package org.daimhim.irregularlayout;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.example.used.decoration.DecorationBuilder;
import com.example.used.decoration.GridDecoration;
import com.example.used.decoration.RecycleDecoration;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.List;

import timber.log.Timber;


/**
 * 项目名称：net.meyki.skt.ui.newhome
 * 项目版本：skt_sst-v2.4.0
 * 创建时间：2019/2/18 14:31  星期一
 * 创建人：Administrator
 * 修改时间：2019/2/18 14:31  星期一
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class TestActivity extends AppCompatActivity {


    private RecyclerView mRvRecyclerview;
    private SmartRefreshLayout mSrlRefresh;
    private RecommendCategoryAdapter mRecommendCategoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        mRecommendCategoryAdapter = new RecommendCategoryAdapter();
        mRvRecyclerview.setAdapter(mRecommendCategoryAdapter);
        initData();
        mRvRecyclerview.addItemDecoration(DecorationBuilder
                .Builder(new GridDecoration(this))
                .setOrientation(GridDecoration.VERTICAL_CROSS)
                .setTopLine(false)
                .setDividerColor(R.color.cl_000000)
                .setDividerWidth(SystemUtil.dip2px(this, 7))
                .setCustomizedOffsets(new RecycleDecoration.CustomizedOffsets() {
                    @Override
                    public boolean obtainOffsets(Rect itemRect, Rect itemColor, int dividerColor, int dividerWidth, int position) {

                        Pair<Integer, Integer> lIntegerIntegerPair = mRecommendCategoryAdapter.indexOfPosition(position);
                        if (lIntegerIntegerPair.second != -1) {
                            itemRect.set(0,0,0,0);
                            int lItemWidth = mRecommendCategoryAdapter.getChildItem(lIntegerIntegerPair.first, lIntegerIntegerPair.second).getItemWidth();
                            switch (lItemWidth){
                                case 12:
                                    itemRect.left = itemRect.right = dividerWidth;
                                    itemColor.left = itemColor.right = dividerColor;
                                    break;
                                case 6:
                                    break;
                                case 4:
                                    break;
                                case 3:
                                    break;
                            }
                        }
                        return true;
                    }
                })
                .builder());
    }

    private void initData() {

        HomeItemBean lHomeItemBean = new Gson().fromJson(datasur, HomeItemBean.class);

        List<ItemListBean> lItemList = lHomeItemBean.getItemList();
        ItemListBean lItemListBean = null;
        for (int i = lItemList.size() - 1; i >= 0; i--) {
            lItemListBean = lItemList.get(i);
            int lSize = lItemListBean.getDataList().size();
            if (lSize <= 0) {
                lItemList.remove(i);
                continue;
            } else if (lSize > 9) {
                for (int j = lSize - 1; j >= 9; j--) {
                    lItemListBean.getDataList().remove(j);
                }
                lSize = lItemListBean.getDataList().size();
            }
            int[] lRule = getRule(lSize);
            Timber.i("lRule:%s lSize:%s", Arrays.toString(lRule), lSize);
            for (int j = 0; j < lSize; j++) {
                lItemListBean.getDataList().get(j).setItemWidth(lRule[j]);
            }
        }
        Timber.i(lItemList.toString());
        mRecommendCategoryAdapter.onRefresh(lItemList);
    }

    int[] getRule(int dataSize) {
        switch (dataSize) {
            case 1:
                return new int[]{
                        12
                };
            case 2:
                return new int[]{
                        6, 6
                };
            case 3:
                return new int[]{
                        4, 4, 4
                };
            case 4:
                return new int[]{
                        6, 6,
                        6, 6
                };
            case 5:
                return new int[]{
                        4, 4, 4,
                        6, 6
                };
            case 6:
                return new int[]{
                        4, 4, 4,
                        4, 4, 4,
                };
            case 7:
                return new int[]{
                        4, 4, 4,
                        3, 3, 3, 3
                };
            case 8:
                return new int[]{
                        3, 3, 3, 3,
                        3, 3, 3, 3
                };
            case 9:
                return new int[]{
                        4, 4, 4,
                        4, 4, 4,
                        4, 4, 4,
                };
            default:
                break;
        }
        return new int[0];
    }

    private void initView() {
        mRvRecyclerview = (RecyclerView) findViewById(R.id.rv_recyclerview);
        mSrlRefresh = (SmartRefreshLayout) findViewById(R.id.srl_Refresh);
        mSrlRefresh.setEnableLoadMore(false).setEnableRefresh(false);
        mSrlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }
        });
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
            }
        });
    }

    //    String datasur = "{\"apiHttpDomain\":\"http://api.meyki.net/esapi/\",\"itemList\":[{\"dataList\":[{\"defaultSpecName\":\"3000g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"9749cfa2e59d48b09908157c0a07a871\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/8a818867819b4e97ad815f6d3d31528a.jpg\",\"goodsName\":\"达州青脆李子\",\"goodsPrice\":\"99.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-达州市-宣汉县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"2500g/箱\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"aac14ae4932b4aa0ae34be29ceb6c8bb\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/615ebbe846254adbab7a3b4ffba1ed5a.jpg\",\"goodsName\":\"蒲江猕猴桃（优质果）\",\"goodsPrice\":\"0.02\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-成都市-蒲江县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"200g*15袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"6625439e26d646e491d2e6f7feeee5d7\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/73530cc9f74941a8ac1ed0e14a68419a.jpg\",\"goodsName\":\"心特软清真粽子\",\"goodsPrice\":\"119.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-咸阳市-礼泉县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"250g/袋*2\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"0b5936568509453983abd0eb051b1b86\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/2c19897ba45f499db7b97933021a24d4.jpg\",\"goodsName\":\"西域美农一级无花果干\",\"goodsPrice\":\"33.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"新疆维吾尔自治区-阿克苏地区-阿克苏市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"250g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"94ab57d118514946a53f4af2120575c0\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/e982e2781c424ec18f92a33b5a70f269.jpg\",\"goodsName\":\"瑞丰野生蓝莓果干\",\"goodsPrice\":\"55.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"黑龙江省-伊春市-翠峦区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"250g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"3252d190e53e4b5ab448504842eba0cb\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/f93c94fb014a4c69baa561c5aeb9b641.jpg\",\"goodsName\":\"半个芒果芒果干\",\"goodsPrice\":\"69.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"广东省-深圳市-宝安区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"18斤左右/箱\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"f3db1f83ad2f4c0380e9f2ab948b8d60\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/eb26144050ee49faa004a57013f756b9.jpg\",\"goodsName\":\"菠萝蜜\",\"goodsPrice\":\"70.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"海南省-省直辖县级行政单位-万宁市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"125g/罐*6/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"4021b5595a4640d993b4c35fadc7f1e0\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/7cd73e6357404b35a57d30511018a0dc.jpg\",\"goodsName\":\"富岗碧根果干果\",\"goodsPrice\":\"149.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-石家庄市-赞皇县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"2500g/箱（一级果）\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"3d0aca3eae564b1380986de577b00fc3\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/f195a96fe7cd4065a17cbea0614f0f13.jpg\",\"goodsName\":\"彭州百香果\",\"goodsPrice\":\"77.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-成都市-彭州市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"2500g/箱\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"441210a98e914d198d4ae160cc4862c0\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a8fb70c3d0eb4838aecebdeb19d7ef92.jpg\",\"goodsName\":\"蒲江猕猴桃（特级果）\",\"goodsPrice\":\"112.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"1\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-成都市-蒲江县\",\"sendTime\":\"\"}],\"goodsId\":\"\",\"imgUrl\":\"\",\"itemId\":\"1\",\"itemName\":\"时令抢鲜\",\"itemNum\":\"\",\"status\":\"\"},{\"dataList\":[{\"defaultSpecName\":\"230g/袋*6\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"a2c035d8e9ea43d8880a6be0ea3605dc\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/b63bf24b7bc54f28a248219f8cdd9908.jpg\",\"goodsName\":\"哈尔滨香肠\",\"goodsPrice\":\"95.50\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"黑龙江省-哈尔滨市-南岗区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500克/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"40e485d0ffd14d47bf68920948ba1ead\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/2f5ce1763ced4b4093931f6bc9e0e3b8.jpg\",\"goodsName\":\"凤牌特级功夫红茶\",\"goodsPrice\":\"98.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"云南省-临沧市-凤庆县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"200g/包*10\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"b1bce3042ff947f2add745707ab13064\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/0e98e003454449ee9c89bee9acd29c94.jpg\",\"goodsName\":\"延吉明太鱼皮\",\"goodsPrice\":\"50.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"吉林省-延边朝鲜族自治州-延吉市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"250g/罐*2\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"1739216feb5447bf815824557ddf1c46\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/0ea6d75f566a4d54b795044df0ac34b1.jpg\",\"goodsName\":\" 桦褐孔菌\",\"goodsPrice\":\"100.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"吉林省-白山市-靖宇县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"120g/瓶\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"3152429030b94405987baf6e882f2a98\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/bddb21d80a4e4cccbc90b0eae4c012d2.jpg\",\"goodsName\":\"石谷灵芝孢子粉\",\"goodsPrice\":\"158.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"广东省-河源市-和平县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"150*5包\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"8dcd292b4bf04657a0635e43538311db\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/80e97d6a39344981acec7652a52e6110.jpg\",\"goodsName\":\"定襄驴肉\",\"goodsPrice\":\"65.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山西省-忻州市-定襄县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"五斤/份\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"799a68bcf28b418f84ad5a45c7c0297d\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/071b0a09b9314847ad2bf0f08eb4c2f7.jpg\",\"goodsName\":\"兴县熏枣\",\"goodsPrice\":\"50.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山西省-吕梁市-兴县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"200g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"2f52120d9f6c4e11a15b4539c0f55679\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/45a333a5e7cd4e78baacfc3b6ad16ffb.jpg\",\"goodsName\":\"桂林精品米粉\",\"goodsPrice\":\"22.50\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"广西壮族自治区-桂林市-叠彩区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"250g*3罐/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"c00dce15d48349dab16bb40fa6f660ac\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/63ed58f4e9f34d2dad5aa6403bc9e7fb.jpg\",\"goodsName\":\"小波杏脯 \",\"goodsPrice\":\"70.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山西省-忻州市-原平市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"250g/罐\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"27c861b1ebd74518bc892551a17d2688\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/47b0845e7f7949f2a13868a61ccfdd30.jpg\",\"goodsName\":\"泉州小青柑\",\"goodsPrice\":\"39.90\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"4\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"福建省-泉州市-安溪县\",\"sendTime\":\"\"}],\"goodsId\":\"\",\"imgUrl\":\"\",\"itemId\":\"4\",\"itemName\":\"特色推荐\",\"itemNum\":\"\",\"status\":\"\"},{\"dataList\":[{\"defaultSpecName\":\"100g/罐*2/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"bda4df88180b446d816c94951f420baa\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/9ed25bce48044812810b5266da774eeb.jpg\",\"goodsName\":\"永登苦水玫瑰花茶\",\"goodsPrice\":\"64.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"甘肃省-兰州市-永登县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g*2袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"1fe24ede71e94198a259ec39efcf2627\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/215c4472238344788b46001fc4b9633c.jpg\",\"goodsName\":\"开心果\",\"goodsPrice\":\"120.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"新疆维吾尔自治区-伊犁哈萨克自治州-伊宁市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"490g/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"443e356937274dcd82576ff207f87d6e\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/6c71ec85321d4d329fdb98d18f656ad1.jpg\",\"goodsName\":\"泾阳手筑茯砖茶\",\"goodsPrice\":\"88.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-咸阳市-泾阳县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"5g*100包/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"775e0b3ba19649f6be25930eae579d3b\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/4249f62feb6f4c3488e4e0142fb96982.jpg\",\"goodsName\":\"荞梓育苦荞茶\",\"goodsPrice\":\"79.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"甘肃省-白银市-会宁县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"200g/罐\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"2caa95991de747e6bdc3bab4c057e0b6\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/63d56fa3f1984558892818d20316d320.jpg\",\"goodsName\":\"潮安通天香\",\"goodsPrice\":\"158.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"广东省-潮州市-潮安区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"40朵/盒*2件\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"2b7f8088f0bf42fb9236e6f1586c4f27\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/976c7e7ba192431c8e250a3513f5f04e.jpg\",\"goodsName\":\"徽州皇菊80朵\",\"goodsPrice\":\"113.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"安徽省-黄山市-歙县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"50g/罐\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"4e0f23d3e63247c7afc903332346b873\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/bd1e8ab6ae9749649a47835c509a94ce.jpg\",\"goodsName\":\"湾里香胎菊\",\"goodsPrice\":\"28.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"浙江省-嘉兴市-桐乡市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"95g/罐\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"ccc5625af8c6415da2e77a67411dd8e8\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/05a79899d60947cdbb4d2bedf66f74cb.jpg\",\"goodsName\":\"胎菊茉莉花组合茶\",\"goodsPrice\":\"29.80\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"江苏省-盐城市-亭湖区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"425g*8罐\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"0ce34e819b1c4f7ebc350442a426cb13\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/67f19e62a37f401391fff567a74f8c2c.jpg\",\"goodsName\":\"带澳飞菠萝罐头\",\"goodsPrice\":\"98.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"福建省-漳州市-龙海市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"9dbaa044a3a44a03bf944d6d56ac6322\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/fcbb5e82cd7d4c199445636f43b6bb1f.jpg\",\"goodsName\":\"羌家风味五花肉\",\"goodsPrice\":\"58.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"5\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-绵阳市-平武县\",\"sendTime\":\"\"}],\"goodsId\":\"\",\"imgUrl\":\"\",\"itemId\":\"5\",\"itemName\":\"人气热销\",\"itemNum\":\"\",\"status\":\"\"},{\"dataList\":[{\"defaultSpecName\":\"250g*5瓶\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"1ce55ee999da4da2a62b32b73065d8df\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/833cac68d5084679b7242b32008239e1.jpg\",\"goodsName\":\"忆乡情辣椒酱\",\"goodsPrice\":\"60.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"6\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"江西省-吉安市-新干县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"a85f5eb3a43b454b8c600da57259280a\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/e6d8511353ba4b098a78f23a17e60858.jpg\",\"goodsName\":\"临沂农家大红袍花椒\",\"goodsPrice\":\"98.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"6\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山东省-临沂市-罗庄区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/瓶\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"96f7411e7b9d46278385b7e9015fc3b2\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/42fc0508e4f94feeb70190744a7fd1a7.jpg\",\"goodsName\":\"陇南自榨核桃油\",\"goodsPrice\":\"50.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"6\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"甘肃省-陇南市-成县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"10mlx10支/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"d997ddd57d024081a0f141014372f685\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/5216ba9322494e3a9d362a494f90c198.jpg\",\"goodsName\":\"旅行佐餐苦荞麦香醋\",\"goodsPrice\":\"18.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"6\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-延安市-吴起县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"225g/瓶*3\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"f381a73cc69944d0bd012aa753241170\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/0800da0d77e64b4eb35831aa8e77d119.jpg\",\"goodsName\":\"家家德亿源招牌鲜椒酱\",\"goodsPrice\":\"44.80\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"6\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-廊坊市-广阳区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"1000g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"8f32041437fd4ac59f5257ce242a02a4\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/00977b1ddd5f47efae6c88b549d75069.jpg\",\"goodsName\":\"龙州八角\",\"goodsPrice\":\"41.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"6\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"广西壮族自治区-崇左市-龙州县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"200g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"3bb326554d6b4953b2cbcdfec40ccc2c\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/bfe77599aad6472480702d9df7f8bcdf.jpg\",\"goodsName\":\"久芳大红袍花椒\",\"goodsPrice\":\"56.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"6\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-渭南市-韩城市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"13809513f076413ba840f15a6c48e190\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/d1bd33e58b7a45ea998bfced58d97d1d.jpg\",\"goodsName\":\"富宁八角\",\"goodsPrice\":\"60.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"6\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"云南省-文山壮族苗族自治州-富宁县\",\"sendTime\":\"\"}],\"goodsId\":\"\",\"imgUrl\":\"\",\"itemId\":\"6\",\"itemName\":\"调味首选\",\"itemNum\":\"\",\"status\":\"\"},{\"dataList\":[{\"defaultSpecName\":\"50g/*4罐\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"2520f9ed15d241e99e15451586ceda94\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/3cd4d18ed5644577b0b6dc3d9b46afe6.jpg\",\"goodsName\":\"万荣秦冠苹果干\",\"goodsPrice\":\"99.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"7\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山西省-运城市-万荣县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"8—10斤/箱\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"bd4eed67679f45bd84915e5bfdb7d308\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/339a3549f59c420e8ee1a086c9c453de.jpg\",\"goodsName\":\"火焰蜜瓜\",\"goodsPrice\":\"88.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"7\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"新疆维吾尔自治区-吐鲁番市-托克逊县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"1080g/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"cdbc0562dbfa4d549d9550e5e6c07491\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/3c280dc0083b474f97b99f8b782fd1df.jpg\",\"goodsName\":\"白云乡海红果\",\"goodsPrice\":\"65.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"7\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-榆林市-府谷县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"5斤/箱\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"be4c42d02539438eb0b005757e05e789\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/307ab912da6e43f2905d892603421bf7.jpg\",\"goodsName\":\"凭祥百香果\",\"goodsPrice\":\"37.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"7\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"广西壮族自治区-崇左市-凭祥市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"50g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"c7d6983836454e4eb1ba43c6ada5894c\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/650695a808144ed885a3eb771aa802ce.jpg\",\"goodsName\":\"秦苑脆冬枣\",\"goodsPrice\":\"12.60\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"7\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-渭南市-大荔县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"80g/罐*3\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"0955731aa3af46d7a2cf237c958f5755\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/82c0397837324342b0122ab86433a9df.jpg\",\"goodsName\":\"大荔脆冬枣罐装\",\"goodsPrice\":\"54.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"7\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-渭南市-大荔县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"130g/盒*4\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"ead2f57b44524c01a637fd5c51c9bc62\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a73eb86d342f4ac0bafad090b3603be8.jpg\",\"goodsName\":\"四川蓝莓\",\"goodsPrice\":\"76.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"7\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-成都市-郫县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"150g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"756ad432c5a2444582c3191f4bbf2578\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/00d08db5694d46f983aaa6a49738c82e.jpg\",\"goodsName\":\"平武美味牛肝菌\",\"goodsPrice\":\"46.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"7\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-绵阳市-平武县\",\"sendTime\":\"\"}],\"goodsId\":\"\",\"imgUrl\":\"\",\"itemId\":\"7\",\"itemName\":\"绿色果蔬\",\"itemNum\":\"\",\"status\":\"\"},{\"dataList\":[{\"defaultSpecName\":\"500g/袋*5/箱\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"878b0438da754ab2aadcc11607bd2ee6\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/fe166f317bf84ee1bfd655d2846595b2.jpg\",\"goodsName\":\"兴唐牌大红枣\",\"goodsPrice\":\"113.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"8\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-石家庄市-行唐县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"1根/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"89a37353021f4bdf8ed1c0057c9346b5\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/309944fd4dde4ce78f826b72a473018f.jpg\",\"goodsName\":\"图们人参\",\"goodsPrice\":\"60.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"8\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"吉林省-延边朝鲜族自治州-图们市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"322570aa7efe46d48682230107d82e93\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/50e409d2fb99489e858009027ca6ff46.jpg\",\"goodsName\":\"滕州龙眼干\",\"goodsPrice\":\"25.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"8\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山东省-枣庄市-滕州市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g／盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"a1e3283175e940ccbaeba5afa91cb53d\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/06dc111f8f594c35b52439f3ee84ba38.jpg\",\"goodsName\":\"野生木耳\",\"goodsPrice\":\"98.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"8\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"黑龙江省-双鸭山市-尖山区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"250g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"f2ea2d5db2e34b96ad2b57307983e200\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/023b03da84d24c1984efd2c360e444ca.jpg\",\"goodsName\":\"潮安虾干\",\"goodsPrice\":\"50.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"8\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"广东省-潮州市-潮安区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"1000g/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"388650b841b049b88e3787ecd40cc37a\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/445f21e8fec14ed2ad8e7130f6ca35c3.jpg\",\"goodsName\":\"田田牌薄皮核桃\",\"goodsPrice\":\"88.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"8\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-石家庄市-行唐县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"5斤/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"ae094afd98214c4c8778fe793d57dd4b\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/de2b39ddfa014e8090f54f8760c377bd.jpg\",\"goodsName\":\"甘洛薄皮核桃\",\"goodsPrice\":\"100.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"8\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-凉山彝族自治州-甘洛县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/盒*2\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"89600fa4eff84b84ac9b90572998883b\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/889733401e69440fa0cefdc87d5b21b5.jpg\",\"goodsName\":\"唐明园金腰带大红枣\",\"goodsPrice\":\"154.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"8\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山西省-临汾市-尧都区\",\"sendTime\":\"\"}],\"goodsId\":\"\",\"imgUrl\":\"\",\"itemId\":\"8\",\"itemName\":\"精品干货\",\"itemNum\":\"\",\"status\":\"\"},{\"dataList\":[{\"defaultSpecName\":\"5.4斤/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"ebae65c0dc2441d3aaad92579cd2bbce\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/dbe8fcd54a1c48b590391c7784310d6c.jpg\",\"goodsName\":\"丫丫爱米有机鸭稻母婴米5.4斤装\",\"goodsPrice\":\"108.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"黑龙江省-哈尔滨市-五常市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"400g/袋*5\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"7d0419183eea43379b7ebf7caa6b9220\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a72fea57bcd94177bf3c2adfa86a51cc.jpg\",\"goodsName\":\"蔚县暖泉玉米渣2kg\",\"goodsPrice\":\"30.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-张家口市-蔚县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"396eb75bdc1147dfb63b4b31996012c0\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/56cd91cdc66b4ad6854a752e13f57dc2.jpg\",\"goodsName\":\"山亭薏米\",\"goodsPrice\":\"15.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山东省-枣庄市-山亭区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"1500g/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"1090d7fa1cba4a2e9c17703b309cd074\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/7c84fd0566c04ec18825b7920bda5ad6.jpg\",\"goodsName\":\"中江手工空心挂面\",\"goodsPrice\":\"52.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-德阳市-中江县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g*2罐/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"6f4c022889094e069ab423030b22d7e2\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/3b3408fdfdc4418f9d63d814892a19d5.jpg\",\"goodsName\":\"活性鲜蜂蜜\",\"goodsPrice\":\"118.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"山东省-潍坊市-临朐县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g*5袋/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"f62153e646cb497ba4fc2fe4d8fe3aa5\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/044f4b680a9a4deab09eb294a50df15b.jpg\",\"goodsName\":\"洋县黑米\",\"goodsPrice\":\"50.00\",\"goodsUnit\":\"盒\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-汉中市-洋县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"250g/包\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"fc76c2e416ed40e592ca4f65067c6f91\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a29017add143481a806ad59a0f5122a5.jpg\",\"goodsName\":\"野生浮小麦\",\"goodsPrice\":\"25.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"湖南省-衡阳市-常宁市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"2000g/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"8948e8466a8844188fc4112a91b2e5ab\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/f9e3ff1afb994cd6b114080b3a8a2278.jpg\",\"goodsName\":\"富岗蔬菜面\",\"goodsPrice\":\"105.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-石家庄市-藁城市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"红色简装405g/袋*5\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"fc62be1470164e569b629c19ec1c5d3c\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/25e849c2b26e48aabb89b872eed9d0e2.jpg\",\"goodsName\":\"姚文明豆糊面\",\"goodsPrice\":\"22.50\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"9\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-张家口市-蔚县\",\"sendTime\":\"\"}],\"goodsId\":\"\",\"imgUrl\":\"\",\"itemId\":\"9\",\"itemName\":\"原生态土特产\",\"itemNum\":\"\",\"status\":\"\"},{\"dataList\":[{\"defaultSpecName\":\"1800g/件\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"3b00988c5f5346a2957b8d1638876e01\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/ca6e92f30ca1466eb066e1f468020ba3.jpg\",\"goodsName\":\"国琳麻饼\",\"goodsPrice\":\"67.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"四川省-成都市-金牛区\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g*3瓶/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"d479b47080dd492783a551b172ce62f6\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/3d58dfb38ac34f12b9e5764d3eb915ca.jpg\",\"goodsName\":\"镇巴罗氏红豆腐\",\"goodsPrice\":\"41.70\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"陕西省-汉中市-镇巴县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"eb7cced422034b20ab5035e5e4cc9111\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/c6b6127bc3b947538b4d3a9a6cff03b5.jpg\",\"goodsName\":\"湖北风干雪魔芋丝\",\"goodsPrice\":\"43.50\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"湖北省-十堰市-郧西县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"100g*5袋/箱\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"e8261f0e4ac0405bbdd7ca439c12147e\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/8c9007bab5a546aba6dfad866884d20d.jpg\",\"goodsName\":\"赛羡雪梅肉\",\"goodsPrice\":\"20.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"甘肃省-庆阳市-宁县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"100g/袋*10/盒\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"33c66ff2d1744ea6bcbe37c82b7c0d3e\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/7123ca0606b84c299ab47ced2ec7d936.jpg\",\"goodsName\":\"清粒源雪梨干\",\"goodsPrice\":\"65.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-石家庄市-行唐县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/包\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"ff24ee651a1f4880ba32d92a8f6846e4\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/0670d779bf524b1e81d3633c779ce9e5.jpg\",\"goodsName\":\"野生干枇杷果\",\"goodsPrice\":\"29.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"湖南省-衡阳市-常宁市\",\"sendTime\":\"\"},{\"defaultSpecName\":\"70g/袋\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"d09ced1a187c4bd98ad87f20df212f05\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a0c7108de68f4acd859770a273675e16.jpg\",\"goodsName\":\"富岗板栗仁\",\"goodsPrice\":\"16.90\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"河北省-石家庄市-赞皇县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"500g/袋*3\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"0d3a60b3a5e14bc8837c3c7111112285\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/52bbde6cdc444af6a71eec0fcd4b706e.jpg\",\"goodsName\":\"拜泉八宝粥\",\"goodsPrice\":\"54.00\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"黑龙江省-齐齐哈尔市-拜泉县\",\"sendTime\":\"\"},{\"defaultSpecName\":\"祁阳味鱼排微辣\",\"endTime\":\"\",\"goodsCate\":\"\",\"goodsId\":\"7707202d87924975b1877f8ca75b736f\",\"goodsImg\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/093e6c9e51344e71ae4cd33c70dc5742.jpg\",\"goodsName\":\"曲渔坊蒸制鳙鱼\",\"goodsPrice\":\"29.50\",\"goodsUnit\":\"件\",\"isConsumer\":\"0\",\"isNew\":\"0\",\"itemId\":\"10\",\"leftTime\":\"2019-02-28 14:29:37\",\"orderSource\":\"0\",\"origin\":\"湖南省-永州市-祁阳县\",\"sendTime\":\"\"}],\"goodsId\":\"\",\"imgUrl\":\"\",\"itemId\":\"10\",\"itemName\":\"名优小吃\",\"itemNum\":\"\",\"status\":\"\"}],\"imgDomain\":\"https://elife-img.oss-cn-hangzhou.aliyuncs.com/\"}";
    String datasur = "{\n" +
            "\t\"apiHttpDomain\": \"http://api.meyki.net/esapi/\",\n" +
            "\t\"itemList\": [{\n" +
            "\t\t\"dataList\": [{\n" +
            "\t\t\t\"defaultSpecName\": \"3000g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"9749cfa2e59d48b09908157c0a07a871\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/8a818867819b4e97ad815f6d3d31528a.jpg\",\n" +
            "\t\t\t\"goodsName\": \"达州青脆李子\",\n" +
            "\t\t\t\"goodsPrice\": \"99.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-达州市-宣汉县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"2500g/箱\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"aac14ae4932b4aa0ae34be29ceb6c8bb\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/615ebbe846254adbab7a3b4ffba1ed5a.jpg\",\n" +
            "\t\t\t\"goodsName\": \"蒲江猕猴桃（优质果）\",\n" +
            "\t\t\t\"goodsPrice\": \"0.02\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-成都市-蒲江县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"200g*15袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"6625439e26d646e491d2e6f7feeee5d7\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/73530cc9f74941a8ac1ed0e14a68419a.jpg\",\n" +
            "\t\t\t\"goodsName\": \"心特软清真粽子\",\n" +
            "\t\t\t\"goodsPrice\": \"119.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-咸阳市-礼泉县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"250g/袋*2\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"0b5936568509453983abd0eb051b1b86\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/2c19897ba45f499db7b97933021a24d4.jpg\",\n" +
            "\t\t\t\"goodsName\": \"西域美农一级无花果干\",\n" +
            "\t\t\t\"goodsPrice\": \"33.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"新疆维吾尔自治区-阿克苏地区-阿克苏市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"250g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"94ab57d118514946a53f4af2120575c0\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/e982e2781c424ec18f92a33b5a70f269.jpg\",\n" +
            "\t\t\t\"goodsName\": \"瑞丰野生蓝莓果干\",\n" +
            "\t\t\t\"goodsPrice\": \"55.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"黑龙江省-伊春市-翠峦区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"250g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"3252d190e53e4b5ab448504842eba0cb\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/f93c94fb014a4c69baa561c5aeb9b641.jpg\",\n" +
            "\t\t\t\"goodsName\": \"半个芒果芒果干\",\n" +
            "\t\t\t\"goodsPrice\": \"69.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"广东省-深圳市-宝安区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"18斤左右/箱\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"f3db1f83ad2f4c0380e9f2ab948b8d60\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/eb26144050ee49faa004a57013f756b9.jpg\",\n" +
            "\t\t\t\"goodsName\": \"菠萝蜜\",\n" +
            "\t\t\t\"goodsPrice\": \"70.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"海南省-省直辖县级行政单位-万宁市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"125g/罐*6/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"4021b5595a4640d993b4c35fadc7f1e0\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/7cd73e6357404b35a57d30511018a0dc.jpg\",\n" +
            "\t\t\t\"goodsName\": \"富岗碧根果干果\",\n" +
            "\t\t\t\"goodsPrice\": \"149.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"河北省-石家庄市-赞皇县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"2500g/箱（一级果）\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"3d0aca3eae564b1380986de577b00fc3\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/f195a96fe7cd4065a17cbea0614f0f13.jpg\",\n" +
            "\t\t\t\"goodsName\": \"彭州百香果\",\n" +
            "\t\t\t\"goodsPrice\": \"77.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-成都市-彭州市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"2500g/箱\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"441210a98e914d198d4ae160cc4862c0\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a8fb70c3d0eb4838aecebdeb19d7ef92.jpg\",\n" +
            "\t\t\t\"goodsName\": \"蒲江猕猴桃（特级果）\",\n" +
            "\t\t\t\"goodsPrice\": \"112.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"1\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-成都市-蒲江县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"1\",\n" +
            "\t\t\"itemName\": \"时令抢鲜\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t}, {\n" +
            "\t\t\"dataList\": [{\n" +
            "\t\t\t\"defaultSpecName\": \"500克/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"40e485d0ffd14d47bf68920948ba1ead\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/2f5ce1763ced4b4093931f6bc9e0e3b8.jpg\",\n" +
            "\t\t\t\"goodsName\": \"凤牌特级功夫红茶\",\n" +
            "\t\t\t\"goodsPrice\": \"98.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"云南省-临沧市-凤庆县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"200g/包*10\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"b1bce3042ff947f2add745707ab13064\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/0e98e003454449ee9c89bee9acd29c94.jpg\",\n" +
            "\t\t\t\"goodsName\": \"延吉明太鱼皮\",\n" +
            "\t\t\t\"goodsPrice\": \"50.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"吉林省-延边朝鲜族自治州-延吉市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"250g/罐*2\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"1739216feb5447bf815824557ddf1c46\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/0ea6d75f566a4d54b795044df0ac34b1.jpg\",\n" +
            "\t\t\t\"goodsName\": \" 桦褐孔菌\",\n" +
            "\t\t\t\"goodsPrice\": \"100.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"吉林省-白山市-靖宇县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"120g/瓶\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"3152429030b94405987baf6e882f2a98\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/bddb21d80a4e4cccbc90b0eae4c012d2.jpg\",\n" +
            "\t\t\t\"goodsName\": \"石谷灵芝孢子粉\",\n" +
            "\t\t\t\"goodsPrice\": \"158.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"广东省-河源市-和平县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"150*5包\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"8dcd292b4bf04657a0635e43538311db\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/80e97d6a39344981acec7652a52e6110.jpg\",\n" +
            "\t\t\t\"goodsName\": \"定襄驴肉\",\n" +
            "\t\t\t\"goodsPrice\": \"65.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"山西省-忻州市-定襄县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"五斤/份\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"799a68bcf28b418f84ad5a45c7c0297d\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/071b0a09b9314847ad2bf0f08eb4c2f7.jpg\",\n" +
            "\t\t\t\"goodsName\": \"兴县熏枣\",\n" +
            "\t\t\t\"goodsPrice\": \"50.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"山西省-吕梁市-兴县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"200g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"2f52120d9f6c4e11a15b4539c0f55679\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/45a333a5e7cd4e78baacfc3b6ad16ffb.jpg\",\n" +
            "\t\t\t\"goodsName\": \"桂林精品米粉\",\n" +
            "\t\t\t\"goodsPrice\": \"22.50\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"广西壮族自治区-桂林市-叠彩区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"250g*3罐/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"c00dce15d48349dab16bb40fa6f660ac\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/63ed58f4e9f34d2dad5aa6403bc9e7fb.jpg\",\n" +
            "\t\t\t\"goodsName\": \"小波杏脯 \",\n" +
            "\t\t\t\"goodsPrice\": \"70.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"山西省-忻州市-原平市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"250g/罐\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"27c861b1ebd74518bc892551a17d2688\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/47b0845e7f7949f2a13868a61ccfdd30.jpg\",\n" +
            "\t\t\t\"goodsName\": \"泉州小青柑\",\n" +
            "\t\t\t\"goodsPrice\": \"39.90\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"4\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"福建省-泉州市-安溪县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"4\",\n" +
            "\t\t\"itemName\": \"特色推荐\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t}, {\n" +
            "\t\t\"dataList\": [{\n" +
            "\t\t\t\"defaultSpecName\": \"490g/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"443e356937274dcd82576ff207f87d6e\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/6c71ec85321d4d329fdb98d18f656ad1.jpg\",\n" +
            "\t\t\t\"goodsName\": \"泾阳手筑茯砖茶\",\n" +
            "\t\t\t\"goodsPrice\": \"88.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"5\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-咸阳市-泾阳县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"5g*100包/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"775e0b3ba19649f6be25930eae579d3b\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/4249f62feb6f4c3488e4e0142fb96982.jpg\",\n" +
            "\t\t\t\"goodsName\": \"荞梓育苦荞茶\",\n" +
            "\t\t\t\"goodsPrice\": \"79.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"5\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"甘肃省-白银市-会宁县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"200g/罐\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"2caa95991de747e6bdc3bab4c057e0b6\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/63d56fa3f1984558892818d20316d320.jpg\",\n" +
            "\t\t\t\"goodsName\": \"潮安通天香\",\n" +
            "\t\t\t\"goodsPrice\": \"158.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"5\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"广东省-潮州市-潮安区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"40朵/盒*2件\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"2b7f8088f0bf42fb9236e6f1586c4f27\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/976c7e7ba192431c8e250a3513f5f04e.jpg\",\n" +
            "\t\t\t\"goodsName\": \"徽州皇菊80朵\",\n" +
            "\t\t\t\"goodsPrice\": \"113.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"5\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"安徽省-黄山市-歙县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"50g/罐\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"4e0f23d3e63247c7afc903332346b873\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/bd1e8ab6ae9749649a47835c509a94ce.jpg\",\n" +
            "\t\t\t\"goodsName\": \"湾里香胎菊\",\n" +
            "\t\t\t\"goodsPrice\": \"28.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"5\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"浙江省-嘉兴市-桐乡市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"95g/罐\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"ccc5625af8c6415da2e77a67411dd8e8\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/05a79899d60947cdbb4d2bedf66f74cb.jpg\",\n" +
            "\t\t\t\"goodsName\": \"胎菊茉莉花组合茶\",\n" +
            "\t\t\t\"goodsPrice\": \"29.80\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"5\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"江苏省-盐城市-亭湖区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"425g*8罐\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"0ce34e819b1c4f7ebc350442a426cb13\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/67f19e62a37f401391fff567a74f8c2c.jpg\",\n" +
            "\t\t\t\"goodsName\": \"带澳飞菠萝罐头\",\n" +
            "\t\t\t\"goodsPrice\": \"98.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"5\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"福建省-漳州市-龙海市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"500g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"9dbaa044a3a44a03bf944d6d56ac6322\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/fcbb5e82cd7d4c199445636f43b6bb1f.jpg\",\n" +
            "\t\t\t\"goodsName\": \"羌家风味五花肉\",\n" +
            "\t\t\t\"goodsPrice\": \"58.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"5\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-绵阳市-平武县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"5\",\n" +
            "\t\t\"itemName\": \"人气热销\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t}, {\n" +
            "\t\t\"dataList\": [ {\n" +
            "\t\t\t\"defaultSpecName\": \"500g/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"a85f5eb3a43b454b8c600da57259280a\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/e6d8511353ba4b098a78f23a17e60858.jpg\",\n" +
            "\t\t\t\"goodsName\": \"临沂农家大红袍花椒\",\n" +
            "\t\t\t\"goodsPrice\": \"98.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"6\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"山东省-临沂市-罗庄区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"500g/瓶\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"96f7411e7b9d46278385b7e9015fc3b2\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/42fc0508e4f94feeb70190744a7fd1a7.jpg\",\n" +
            "\t\t\t\"goodsName\": \"陇南自榨核桃油\",\n" +
            "\t\t\t\"goodsPrice\": \"50.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"6\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"甘肃省-陇南市-成县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"10mlx10支/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"d997ddd57d024081a0f141014372f685\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/5216ba9322494e3a9d362a494f90c198.jpg\",\n" +
            "\t\t\t\"goodsName\": \"旅行佐餐苦荞麦香醋\",\n" +
            "\t\t\t\"goodsPrice\": \"18.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"6\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-延安市-吴起县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"225g/瓶*3\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"f381a73cc69944d0bd012aa753241170\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/0800da0d77e64b4eb35831aa8e77d119.jpg\",\n" +
            "\t\t\t\"goodsName\": \"家家德亿源招牌鲜椒酱\",\n" +
            "\t\t\t\"goodsPrice\": \"44.80\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"6\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"河北省-廊坊市-广阳区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"1000g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"8f32041437fd4ac59f5257ce242a02a4\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/00977b1ddd5f47efae6c88b549d75069.jpg\",\n" +
            "\t\t\t\"goodsName\": \"龙州八角\",\n" +
            "\t\t\t\"goodsPrice\": \"41.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"6\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"广西壮族自治区-崇左市-龙州县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"200g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"3bb326554d6b4953b2cbcdfec40ccc2c\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/bfe77599aad6472480702d9df7f8bcdf.jpg\",\n" +
            "\t\t\t\"goodsName\": \"久芳大红袍花椒\",\n" +
            "\t\t\t\"goodsPrice\": \"56.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"6\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-渭南市-韩城市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"500g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"13809513f076413ba840f15a6c48e190\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/d1bd33e58b7a45ea998bfced58d97d1d.jpg\",\n" +
            "\t\t\t\"goodsName\": \"富宁八角\",\n" +
            "\t\t\t\"goodsPrice\": \"60.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"6\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"云南省-文山壮族苗族自治州-富宁县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"6\",\n" +
            "\t\t\"itemName\": \"调味首选\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t}, {\n" +
            "\t\t\"dataList\": [{\n" +
            "\t\t\t\"defaultSpecName\": \"1080g/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"cdbc0562dbfa4d549d9550e5e6c07491\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/3c280dc0083b474f97b99f8b782fd1df.jpg\",\n" +
            "\t\t\t\"goodsName\": \"白云乡海红果\",\n" +
            "\t\t\t\"goodsPrice\": \"65.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"7\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-榆林市-府谷县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"5斤/箱\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"be4c42d02539438eb0b005757e05e789\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/307ab912da6e43f2905d892603421bf7.jpg\",\n" +
            "\t\t\t\"goodsName\": \"凭祥百香果\",\n" +
            "\t\t\t\"goodsPrice\": \"37.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"7\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"广西壮族自治区-崇左市-凭祥市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"50g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"c7d6983836454e4eb1ba43c6ada5894c\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/650695a808144ed885a3eb771aa802ce.jpg\",\n" +
            "\t\t\t\"goodsName\": \"秦苑脆冬枣\",\n" +
            "\t\t\t\"goodsPrice\": \"12.60\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"7\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-渭南市-大荔县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"80g/罐*3\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"0955731aa3af46d7a2cf237c958f5755\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/82c0397837324342b0122ab86433a9df.jpg\",\n" +
            "\t\t\t\"goodsName\": \"大荔脆冬枣罐装\",\n" +
            "\t\t\t\"goodsPrice\": \"54.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"7\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-渭南市-大荔县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"130g/盒*4\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"ead2f57b44524c01a637fd5c51c9bc62\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a73eb86d342f4ac0bafad090b3603be8.jpg\",\n" +
            "\t\t\t\"goodsName\": \"四川蓝莓\",\n" +
            "\t\t\t\"goodsPrice\": \"76.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"7\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-成都市-郫县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"150g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"756ad432c5a2444582c3191f4bbf2578\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/00d08db5694d46f983aaa6a49738c82e.jpg\",\n" +
            "\t\t\t\"goodsName\": \"平武美味牛肝菌\",\n" +
            "\t\t\t\"goodsPrice\": \"46.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"7\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-绵阳市-平武县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"7\",\n" +
            "\t\t\"itemName\": \"绿色果蔬\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t}, {\n" +
            "\t\t\"dataList\": [ {\n" +
            "\t\t\t\"defaultSpecName\": \"500g／盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"a1e3283175e940ccbaeba5afa91cb53d\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/06dc111f8f594c35b52439f3ee84ba38.jpg\",\n" +
            "\t\t\t\"goodsName\": \"野生木耳\",\n" +
            "\t\t\t\"goodsPrice\": \"98.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"8\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"黑龙江省-双鸭山市-尖山区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"250g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"f2ea2d5db2e34b96ad2b57307983e200\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/023b03da84d24c1984efd2c360e444ca.jpg\",\n" +
            "\t\t\t\"goodsName\": \"潮安虾干\",\n" +
            "\t\t\t\"goodsPrice\": \"50.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"8\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"广东省-潮州市-潮安区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"1000g/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"388650b841b049b88e3787ecd40cc37a\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/445f21e8fec14ed2ad8e7130f6ca35c3.jpg\",\n" +
            "\t\t\t\"goodsName\": \"田田牌薄皮核桃\",\n" +
            "\t\t\t\"goodsPrice\": \"88.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"8\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"河北省-石家庄市-行唐县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"5斤/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"ae094afd98214c4c8778fe793d57dd4b\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/de2b39ddfa014e8090f54f8760c377bd.jpg\",\n" +
            "\t\t\t\"goodsName\": \"甘洛薄皮核桃\",\n" +
            "\t\t\t\"goodsPrice\": \"100.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"8\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-凉山彝族自治州-甘洛县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"500g/盒*2\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"89600fa4eff84b84ac9b90572998883b\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/889733401e69440fa0cefdc87d5b21b5.jpg\",\n" +
            "\t\t\t\"goodsName\": \"唐明园金腰带大红枣\",\n" +
            "\t\t\t\"goodsPrice\": \"154.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"8\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"山西省-临汾市-尧都区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"8\",\n" +
            "\t\t\"itemName\": \"精品干货\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t}, {\n" +
            "\t\t\"dataList\": [{\n" +
            "\t\t\t\"defaultSpecName\": \"500g*5袋/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"f62153e646cb497ba4fc2fe4d8fe3aa5\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/044f4b680a9a4deab09eb294a50df15b.jpg\",\n" +
            "\t\t\t\"goodsName\": \"洋县黑米\",\n" +
            "\t\t\t\"goodsPrice\": \"50.00\",\n" +
            "\t\t\t\"goodsUnit\": \"盒\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"9\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-汉中市-洋县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"250g/包\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"fc76c2e416ed40e592ca4f65067c6f91\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a29017add143481a806ad59a0f5122a5.jpg\",\n" +
            "\t\t\t\"goodsName\": \"野生浮小麦\",\n" +
            "\t\t\t\"goodsPrice\": \"25.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"9\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"湖南省-衡阳市-常宁市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"2000g/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"8948e8466a8844188fc4112a91b2e5ab\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/f9e3ff1afb994cd6b114080b3a8a2278.jpg\",\n" +
            "\t\t\t\"goodsName\": \"富岗蔬菜面\",\n" +
            "\t\t\t\"goodsPrice\": \"105.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"9\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"河北省-石家庄市-藁城市\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"红色简装405g/袋*5\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"fc62be1470164e569b629c19ec1c5d3c\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/25e849c2b26e48aabb89b872eed9d0e2.jpg\",\n" +
            "\t\t\t\"goodsName\": \"姚文明豆糊面\",\n" +
            "\t\t\t\"goodsPrice\": \"22.50\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"9\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"河北省-张家口市-蔚县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"9\",\n" +
            "\t\t\"itemName\": \"原生态土特产\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t}, {\n" +
            "\t\t\"dataList\": [{\n" +
            "\t\t\t\"defaultSpecName\": \"70g/袋\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"d09ced1a187c4bd98ad87f20df212f05\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/a0c7108de68f4acd859770a273675e16.jpg\",\n" +
            "\t\t\t\"goodsName\": \"富岗板栗仁\",\n" +
            "\t\t\t\"goodsPrice\": \"16.90\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"10\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"河北省-石家庄市-赞皇县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"500g/袋*3\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"0d3a60b3a5e14bc8837c3c7111112285\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/52bbde6cdc444af6a71eec0fcd4b706e.jpg\",\n" +
            "\t\t\t\"goodsName\": \"拜泉八宝粥\",\n" +
            "\t\t\t\"goodsPrice\": \"54.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"10\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"黑龙江省-齐齐哈尔市-拜泉县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"祁阳味鱼排微辣\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"7707202d87924975b1877f8ca75b736f\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/093e6c9e51344e71ae4cd33c70dc5742.jpg\",\n" +
            "\t\t\t\"goodsName\": \"曲渔坊蒸制鳙鱼\",\n" +
            "\t\t\t\"goodsPrice\": \"29.50\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"10\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"湖南省-永州市-祁阳县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"13\",\n" +
            "\t\t\"itemName\": \"名优小吃3\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t},{\n" +
            "\t\t\"dataList\": [{\n" +
            "\t\t\t\"defaultSpecName\": \"1800g/件\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"3b00988c5f5346a2957b8d1638876e01\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/ca6e92f30ca1466eb066e1f468020ba3.jpg\",\n" +
            "\t\t\t\"goodsName\": \"国琳麻饼\",\n" +
            "\t\t\t\"goodsPrice\": \"67.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"10\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-成都市-金牛区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"defaultSpecName\": \"500g*3瓶/盒\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"d479b47080dd492783a551b172ce62f6\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/3d58dfb38ac34f12b9e5764d3eb915ca.jpg\",\n" +
            "\t\t\t\"goodsName\": \"镇巴罗氏红豆腐\",\n" +
            "\t\t\t\"goodsPrice\": \"41.70\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"10\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"陕西省-汉中市-镇巴县\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"12\",\n" +
            "\t\t\"itemName\": \"名优小吃2\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t},{\n" +
            "\t\t\"dataList\": [{\n" +
            "\t\t\t\"defaultSpecName\": \"1800g/件\",\n" +
            "\t\t\t\"endTime\": \"\",\n" +
            "\t\t\t\"goodsCate\": \"\",\n" +
            "\t\t\t\"goodsId\": \"3b00988c5f5346a2957b8d1638876e01\",\n" +
            "\t\t\t\"goodsImg\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/shopPhoto/ca6e92f30ca1466eb066e1f468020ba3.jpg\",\n" +
            "\t\t\t\"goodsName\": \"国琳麻饼\",\n" +
            "\t\t\t\"goodsPrice\": \"67.00\",\n" +
            "\t\t\t\"goodsUnit\": \"件\",\n" +
            "\t\t\t\"isConsumer\": \"0\",\n" +
            "\t\t\t\"isNew\": \"0\",\n" +
            "\t\t\t\"itemId\": \"10\",\n" +
            "\t\t\t\"leftTime\": \"2019-02-28 14:29:37\",\n" +
            "\t\t\t\"orderSource\": \"0\",\n" +
            "\t\t\t\"origin\": \"四川省-成都市-金牛区\",\n" +
            "\t\t\t\"sendTime\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"21\",\n" +
            "\t\t\"itemName\": \"名优小吃3\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t},{\n" +
            "\t\t\"dataList\": [],\n" +
            "\t\t\"goodsId\": \"\",\n" +
            "\t\t\"imgUrl\": \"\",\n" +
            "\t\t\"itemId\": \"20\",\n" +
            "\t\t\"itemName\": \"名优小吃0\",\n" +
            "\t\t\"itemNum\": \"\",\n" +
            "\t\t\"status\": \"\"\n" +
            "\t}],\n" +
            "\t\"imgDomain\": \"https://elife-img.oss-cn-hangzhou.aliyuncs.com/\"\n" +
            "}";
}
