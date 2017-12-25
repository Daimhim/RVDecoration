package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
 *
 * @author Daimhim
 */

public class DecorationBuilder2 {
    private final DecorationParameter P;

    private RecycleDecoration2 mRecycleDecoration2;
    private DisplayMetrics mDisplayMetrics = null;


    private Context mContext;

    public DecorationBuilder2(RecyclerView recyclerView) {
        this(recyclerView, 0);
    }

    public DecorationBuilder2(RecyclerView recyclerView, int lineWidth) {
        this(recyclerView, lineWidth, 0);
    }

    /**
     *
     * @param recyclerView 仅在构造时使用 不会传入RecycleDecoration2对象
     * @param lineWidth  宽度
     * @param lineColor  颜色
     */
    public DecorationBuilder2(RecyclerView recyclerView, int lineWidth, int lineColor) {
        mContext = recyclerView.getContext();
        mRecycleDecoration2 = new RecycleDecoration2();
        mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplayMetrics);
        P = new DecorationParameter((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidth, mDisplayMetrics)
                , ContextCompat.getColor(mContext, lineColor));
        //保证赋值成功
        initDecoration(recyclerView);
    }

    /**
     * 初始化基本的画笔方法
     * @param recyclerView
     */
    private void initDecoration(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager){
            P.orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (layoutManager instanceof GridLayoutManager){

                return;
            }
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            P.orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
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

    public void setLineWidth(int lineWidth) {
        P.lineWidth = lineWidth;
    }

    public void setLineColor(int lineColor) {
        P.lineColor = lineColor;
    }

    public void setParentPadding(int left, int top, int right, int bottom) {
        P.rectWidth.set(left, top, right, bottom);
    }

    public void setParentPaddingColor(int left, int top, int right, int bottom) {
        P.rectColor.set(left, top, right, bottom);
    }

    public RecycleDecoration2 builder() {
        return mRecycleDecoration2;
    }

    /**
     * 单位都是Px 只在入参时添加不同的入口
     */
    public static class DecorationParameter {
        int lineWidth = 0;
        int lineColor = 0;
        /**
         * 定义四个边的边距
         */
        Rect rectWidth = new Rect();
        /**
         * 定义四个边的颜色
         */
        Rect rectColor = new Rect();
        int orientation;
        public DecorationParameter(int lineWidth, int lineColor) {
            this.lineWidth = lineWidth;
            this.lineColor = lineColor;
        }

        public int getLineWidth() {
            return lineWidth;
        }

        public void setLineWidth(int lineWidth) {
            this.lineWidth = lineWidth;
        }

        public int getLineColor() {
            return lineColor;
        }

        public void setLineColor(int lineColor) {
            this.lineColor = lineColor;
        }

        public Rect getRectWidth() {
            return rectWidth;
        }

        public void setRectWidth(Rect rectWidth) {
            this.rectWidth = rectWidth;
        }

        public Rect getRectColor() {
            return rectColor;
        }

        public void setRectColor(Rect rectColor) {
            this.rectColor = rectColor;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }
    }

}
