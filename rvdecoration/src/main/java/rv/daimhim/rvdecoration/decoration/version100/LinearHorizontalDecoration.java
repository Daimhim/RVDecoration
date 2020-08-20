package rv.daimhim.rvdecoration.decoration.version100;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.RecycleDecoration;
import rv.daimhim.rvdecoration.decoration.base.AbsLinearDecoration;

/**
 * 项目名称：rv.daimhim.rvdecoration
 * 项目版本：RVDecoration
 * 创建时间：2019/4/11 16:56  星期四
 * 创建人：Administrator
 * 修改时间：2019/4/11 16:56  星期四
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class LinearHorizontalDecoration extends AbsLinearDecoration implements RecycleDecoration.DrawBeforeTarget,
        RecycleDecoration.MeasureTarget {
    /**
     * 画笔
     */
    private Paint mPaint;
    private final int mSize;
    private final int bothSides;
    private final int mFood;
    private final int mHead;
    private Rect mRect;

    protected LinearHorizontalDecoration(DecorationBuilder.DecorationParams pP) {
        this.mSize = pP.mContext.getResources().getDimensionPixelSize(pP.size);
        this.mFood = pP.mContext.getResources().getDimensionPixelSize(pP.food);
        this.mHead = pP.mContext.getResources().getDimensionPixelSize(pP.head);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(pP.mContext, pP.color));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);
        this.bothSides = pP.mContext.getResources().getDimensionPixelSize(pP.bothSides);
        this.mRect = new Rect();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
//        View childAt;
//        for (int i = 0; i < childCount; i++) {
//            mRect.set(0, 0, 0, 0);
//            childAt = parent.getChildAt(i);
//            getItemOffsets(mRect, childAt, parent, state);
//            DrawHelp.drawLine(c, mRect, mPaint, childAt);
//        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int count = state.getItemCount() - 1; //总长度
        int lChildAdapterPosition = parent.getChildAdapterPosition(view);
        if (lChildAdapterPosition == 0) {
            outRect.set(mHead, bothSides, mSize, bothSides);
        }else if (lChildAdapterPosition == count){
            outRect.set(0, bothSides, mFood, bothSides);
        } else {
            outRect.set(0, bothSides, mSize, bothSides);
        }
    }
}