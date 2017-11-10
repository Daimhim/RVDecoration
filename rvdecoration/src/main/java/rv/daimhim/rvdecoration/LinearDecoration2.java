package rv.daimhim.rvdecoration;

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
    private Context mContext;
    private DisplayMetrics mDisplayMetrics = null;
    private Paint mPaint;
    private LinearLayoutManager mLinearLayoutManager = null;
    @ColorRes
    int lineColor = R.color.cl_00000000;

    int lineWidth;

    public LinearDecoration2(Context context, @ColorRes int lineColor, float lineWidth) {
        mContext = context;
        mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplayMetrics);
        mPaint = new Paint();
        this.lineColor = lineColor;
        this.lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidth, mDisplayMetrics);
        mPaint.setColor(ContextCompat.getColor(mContext, lineColor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(this.lineWidth);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        View childAt = null;
        Rect rect = new Rect();
        for (int i = 0; i < childCount; i++) {
            childAt = parent.getChildAt(i);
            int bottomDecorationHeight = mLinearLayoutManager.getBottomDecorationHeight(childAt);
            int left = childAt.getLeft();
            int bottom = childAt.getBottom();
            float startY = bottom + (bottomDecorationHeight / 2);
            float stopY = bottom + (bottomDecorationHeight / 2);
            int right = childAt.getRight();
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
        if (null == mLinearLayoutManager) {
            mLinearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
        }
        int orientation = mLinearLayoutManager.getOrientation();
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, lineWidth);
        }
        Log.i(TAG, "childLayoutPosition:" + childLayoutPosition + " ---outRect:" + outRect.toString());
    }

}
