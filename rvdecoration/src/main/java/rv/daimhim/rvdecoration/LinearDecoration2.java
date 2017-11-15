package rv.daimhim.rvdecoration;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

/**
 * 使用方法 请看DecorationBuilder类
 * 该类用于LinearLayoutManager
 *
 * @author Daimhim
 * @date 2017/2/16
 */

public class LinearDecoration2 implements RecycleDecoration2.DrawBeforeTarget, RecycleDecoration2.DrawAfterTarget, RecycleDecoration2.MeasureTarget {
    String TAG = "TAG:" + getClass().getSimpleName();

    protected final DecorationBuilder2.DecorationParameter mParameter;
    private Paint mPaint;
    private LinearLayoutManager mLinearLayoutManager = null;


    public LinearDecoration2(Context context,  DecorationBuilder2.DecorationParameter parameter) {
        mParameter = parameter;
        mPaint = new Paint();
        mPaint.setColor(mParameter.lineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mParameter.lineWidth);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        View childAt = null;
        Rect rect = new Rect();
        int bottomDecorationHeight = 0;
        int left = 0;
        int bottom = 0;
        float startY = 0;
        float stopY = 0;
        int right = 0;
        for (int i = 0; i < childCount; i++) {
            childAt = parent.getChildAt(i);
            bottomDecorationHeight = mLinearLayoutManager.getBottomDecorationHeight(childAt);
            left = childAt.getLeft();
            bottom = childAt.getBottom();
            startY = bottom + (bottomDecorationHeight / 2);
            stopY = bottom + (bottomDecorationHeight / 2);
            right = childAt.getRight();
            c.drawLine(left, startY, right, stopY, mPaint);
            rect.setEmpty();
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        if (mParameter.orientation == LinearLayoutManager.VERTICAL) {
            if (childLayoutPosition == 0) {
                outRect.set(mParameter.getRectWidth().left, mParameter.getRectWidth().top,
                        mParameter.getRectWidth().right, mParameter.lineWidth);
            }else if (childLayoutPosition == parent.getChildCount()){
                outRect.set(mParameter.getRectWidth().left, 0,
                        mParameter.getRectWidth().right, mParameter.getRectWidth().bottom);
            }else {
                outRect.set(mParameter.getRectWidth().left, 0,
                        mParameter.getRectWidth().right, mParameter.getLineWidth());
            }
        }else {
//            if (childLayoutPosition == 0) {
//                outRect.set(mParameter.getRectWidth().left, mParameter.getRectWidth().top,
//                        mParameter.lineWidth, mParameter.lineWidth);
//            }else if (childLayoutPosition == parent.getChildCount()){
//                outRect.set(mParameter.getRectWidth().left, 0,
//                        mParameter.getRectWidth().right, mParameter.getRectWidth().bottom);
//            }else {
//                outRect.set(mParameter.getRectWidth().left, 0,
//                        mParameter.getRectWidth().right, mParameter.getLineWidth());
//            }
        }
    }

}
