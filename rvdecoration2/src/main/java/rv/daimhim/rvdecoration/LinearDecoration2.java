package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

/**
 * 使用方法 请看DecorationBuilder类
 * 该类用于LinearLayoutManager
 * Created by Daimhim on 2017/2/16.
 */

public class LinearDecoration2 implements RecycleDecoration2.DrawBeforeTarget, RecycleDecoration2.DrawAfterTarget, RecycleDecoration2.MeasureTarget {

    private Context mContext;
    private DisplayMetrics mDisplayMetrics = null;
    private Paint mPaint;
    @ColorInt
    int lineColor = Color.parseColor("#00000000");

    float lineWidth = 0;

    public LinearDecoration2(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(lineWidth);
        mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplayMetrics);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        View childAt = null;
        int position = -1;
        Rect rect = new Rect();
        for (int i = 0; i < childCount; i++) {
            childAt = parent.getChildAt(i);
            position = parent.getChildAdapterPosition(childAt);
            //当前View是否在可视范围内
            if (childAt.getGlobalVisibleRect(rect)) {
//                childAt.
            }
            rect.setEmpty();
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, lineWidth, mDisplayMetrics));
            }
        }
    }
}
