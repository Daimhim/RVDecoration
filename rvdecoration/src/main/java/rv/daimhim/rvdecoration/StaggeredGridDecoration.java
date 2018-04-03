package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class StaggeredGridDecoration implements RecycleDecoration.DrawBeforeTarget {
    /**
     * 画笔
     */
    private Paint mPaint;
    private Rect mRect;
    private RecycleDecoration.MeasureTarget mMeasureTarget;

    protected StaggeredGridDecoration(Context pContext, @ColorRes int color,
                                      @DimenRes int size, int orientation,int spanCount) {
        int mSize = pContext.getResources().getDimensionPixelSize(size);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(pContext, color));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);

        mRect = new Rect();
        if (orientation == StaggeredGridLayoutManager.VERTICAL){
            mMeasureTarget = new VerticalItemOffsets(spanCount,mSize);
        }else {
            mMeasureTarget = new HorizontalItemOffsets(spanCount,mSize);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        View childAt;
        for (int i = 0; i < childCount; i++) {
            mRect.set(0,0,0,0);
            childAt = parent.getChildAt(i);
            mMeasureTarget.getItemOffsets(mRect,childAt,parent,state);
            DrawHelp.drawLine(c,mRect, mPaint, childAt);
        }
    }

    protected RecycleDecoration.MeasureTarget getMeasureTarget() {
        return mMeasureTarget;
    }

    class VerticalItemOffsets implements RecycleDecoration.MeasureTarget{

        int mSpanCount;
        int mSize;

        protected VerticalItemOffsets(int pSpanCount, int pSize) {
            mSpanCount = pSpanCount;
            mSize = pSize;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (mSpanCount > 1) {
                int lPosition = parent.getChildAdapterPosition(view);
                int lPaddingLeft = parent.getPaddingLeft();
                int lPaddingRight = parent.getPaddingRight();
                float lX = parent.getX();
                float lX1 = view.getX();
                if (lPosition % mSpanCount == 0) {
                    outRect.set(0,0,mSize,mSize);
                } else if (((lPosition - (mSpanCount - 1)) % mSpanCount) == 0) {
                    outRect.set(0,0,0,mSize);
                }else {
                    outRect.set(0,0,mSize,mSize);
                }
            }else {
                outRect.set(0,0,0,mSize);
            }
        }
    }
    class HorizontalItemOffsets implements RecycleDecoration.MeasureTarget{
        int mSpanCount;
        int mSize;

        protected HorizontalItemOffsets(int pSpanCount, int pSize) {
            mSpanCount = pSpanCount;
            mSize = pSize;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (mSpanCount > 1) {
                int lPosition = parent.getChildAdapterPosition(view);
                if (lPosition % mSpanCount == 0) {
                    outRect.set(0,0,mSize,mSize);
                } else if (((lPosition - (mSpanCount - 1)) % mSpanCount) == 0) {
                    outRect.set(0,0,0,mSize);
                }else {
                    outRect.set(0,0,mSize,mSize);
                }
            }else {
                outRect.set(0,0,mSize,0);
            }
        }
    }
}
