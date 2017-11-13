package rv.daimhim.rvdecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 项目名称：com.example.used.decoration
 * 项目版本：usedlibrary
 * 创建人：Daimhim
 * 创建时间：2017/10/26 11:23
 * 修改人：Daimhim
 * 修改时间：2017/10/26 11:23
 * 类描述：
 * 修改备注：
 * @author Daimhim
 */

public class DecorationBuilder2 {
    private final DecorationParameter P;

    private RecycleDecoration2 mRecycleDecoration2;
    private DisplayMetrics mDisplayMetrics = null;


    private Context mContext;

    public DecorationBuilder2(Context context) {
        this(context,0,-1);
    }
    public DecorationBuilder2(Context context,int lineWidth) {
        this(context,lineWidth,-1);
    }
    public DecorationBuilder2(Context context,int lineWidth,int lineColor) {
        mContext = context;
        mRecycleDecoration2 = new RecycleDecoration2();
        P = new DecorationParameter();
        mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplayMetrics);
        P.lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidth, mDisplayMetrics);
        P.lineColor = ContextCompat.getColor(mContext, lineColor);
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

    public void setParentPadding(int left, int top, int right, int bottom){
        P.rectWidth.set(left, top, right, bottom);
    }

    public void setParentPaddingColor(int left, int top, int right, int bottom){
        P.rectColor.set(left, top, right, bottom);
    }

    public RecycleDecoration2 builder(){
        return mRecycleDecoration2;
    }

    /**
     * 单位都是Px 只在入参时添加不同的入口
     */
    public static class DecorationParameter{
        int lineColor = 0;
        int lineWidth = 0;
        /**
         * 定义四个边的边距
         */
        Rect rectWidth = new Rect();
        /**
         * 定义四个边的颜色
         */
        Rect rectColor = new Rect();
    }

}
