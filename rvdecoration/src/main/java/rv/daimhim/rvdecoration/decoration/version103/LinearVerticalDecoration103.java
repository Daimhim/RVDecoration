package rv.daimhim.rvdecoration.decoration.version103;

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
 * @Classname LinearVerticalDecoration103
 * @Description TODO
 * @Date 2019/7/4 17:46
 * @Created by Daimhim
 * Class description Daimhim太懒了什么都没有留下
 */
public class LinearVerticalDecoration103 extends AbsLinearDecoration implements RecycleDecoration.DrawBeforeTarget,
        RecycleDecoration.MeasureTarget {
    /**
     * 画笔
     */
    private Paint mPaint;
    private final int mSize;
    private final int bothSides;
    private final int mFood;
    private final int mHead;
    private final DecorationBuilder.DecorationParams mDecorationParams;

    public LinearVerticalDecoration103(DecorationBuilder.DecorationParams pDecorationParams) {
        mDecorationParams = pDecorationParams;
        this.mSize = mDecorationParams.mContext.getResources().getDimensionPixelSize(mDecorationParams.size);
        this.mFood = mDecorationParams.mContext.getResources().getDimensionPixelSize(mDecorationParams.food);
        this.mHead = mDecorationParams.mContext.getResources().getDimensionPixelSize(mDecorationParams.head);
        this.bothSides = mDecorationParams.mContext.getResources().getDimensionPixelSize(mDecorationParams.bothSides);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(mDecorationParams.mContext, mDecorationParams.color));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int count = state.getItemCount() - 1; //总长度
        int lChildAdapterPosition = parent.getChildAdapterPosition(view);
        if (lChildAdapterPosition == 0) {
            outRect.set(bothSides, mHead, bothSides, mSize);
        }else if (lChildAdapterPosition == count){
            outRect.set(bothSides, 0, bothSides, mFood);
        } else {
            outRect.set(bothSides, 0, bothSides, mSize);
        }
    }
}
