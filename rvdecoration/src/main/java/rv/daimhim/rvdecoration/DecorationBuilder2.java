package rv.daimhim.rvdecoration;

import android.content.Context;

/**
 * 项目名称：com.example.used.decoration
 * 项目版本：usedlibrary
 * 创建人：Daimhim
 * 创建时间：2017/10/26 11:23
 * 修改人：Daimhim
 * 修改时间：2017/10/26 11:23
 * 类描述：
 * 修改备注：
 */

public class DecorationBuilder2 {

    private RecycleDecoration2 mRecycleDecoration2;

    private Context mContext;

    public DecorationBuilder2(Context context) {
        mContext = context;
        mRecycleDecoration2 = new RecycleDecoration2();
    }

    public DecorationBuilder2 linearDecoration(int lineColor, int lineWidth){
        LinearDecoration2 linearDecoration2 = new LinearDecoration2(mContext,lineColor,
                lineWidth);
        mRecycleDecoration2.setMeasureTarget(linearDecoration2);
        mRecycleDecoration2.setDrawAfterTarget(linearDecoration2);
        mRecycleDecoration2.setDrawBeforeTarget(linearDecoration2);
        return this;
    }

    public DecorationBuilder2 setDrawBeforeTarget(RecycleDecoration2.DrawBeforeTarget drawBeforeTarget) {
        mRecycleDecoration2.setDrawBeforeTarget(drawBeforeTarget);
        return this;
    }

    public DecorationBuilder2 setDrawAfterTarget(RecycleDecoration2.DrawAfterTarget drawAfterTarget) {
        mRecycleDecoration2.setDrawAfterTarget(drawAfterTarget);
        return this;
    }

    public DecorationBuilder2 setMeasureTarget(RecycleDecoration2.MeasureTarget measureTarget) {
        mRecycleDecoration2.setMeasureTarget(measureTarget);
        return this;
    }


    public RecycleDecoration2 builder(){
        return mRecycleDecoration2;
    }

}
