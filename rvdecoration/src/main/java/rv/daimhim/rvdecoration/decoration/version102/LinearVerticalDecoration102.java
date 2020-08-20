package rv.daimhim.rvdecoration.decoration.version102;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.DrawHelp;
import rv.daimhim.rvdecoration.RecycleDecoration;
import rv.daimhim.rvdecoration.decoration.base.AbsLinearDecoration;

public class LinearVerticalDecoration102 extends AbsLinearDecoration implements RecycleDecoration.DrawBeforeTarget,
        RecycleDecoration.MeasureTarget  {
    /**
     * 画笔
     */
    private Paint mPaint;
    private final int mSize;
    private Rect mRect;
    private final int bothSides;
    private final int mFood;
    private final int mHead;
    private final int mFoodCount;
    private final int mHeadCount;
    protected LinearVerticalDecoration102(DecorationBuilder.DecorationParams pP) {
        this.mSize = pP.mContext.getResources().getDimensionPixelSize(pP.size);
        this.mFood = pP.mContext.getResources().getDimensionPixelSize(pP.food);
        this.mHead = pP.mContext.getResources().getDimensionPixelSize(pP.head);
        this.bothSides = pP.mContext.getResources().getDimensionPixelSize(pP.bothSides);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(pP.mContext, pP.color));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);
        this.mFoodCount = pP.footCount;
        this.mHeadCount = pP.baseCount;
        mRect = new Rect();
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        View childAt;
        for (int i = 0; i < childCount; i++) {
            mRect.set(0,0,0,0);
            childAt = parent.getChildAt(i);
            getItemOffsets(mRect,childAt,parent,state);
            DrawHelp.drawLine(c,mRect, mPaint, childAt);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int count = state.getItemCount() - 1; //总长度
        int lChildAdapterPosition = parent.getChildAdapterPosition(view);
        if (mHeadCount > lChildAdapterPosition || count - mHeadCount < lChildAdapterPosition){
            return;
        }
        lChildAdapterPosition -= mHeadCount;
        if (lChildAdapterPosition == 0) {
            outRect.set(bothSides, mHead, bothSides, mSize);
        }else if (lChildAdapterPosition == count){
            outRect.set(bothSides, 0, bothSides, mFood);
        } else {
            outRect.set(bothSides, 0, bothSides, mSize);
        }
    }
}