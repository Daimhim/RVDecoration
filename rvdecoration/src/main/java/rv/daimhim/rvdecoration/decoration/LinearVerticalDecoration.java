package rv.daimhim.rvdecoration.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.DrawHelp;
import rv.daimhim.rvdecoration.RecycleDecoration;
import rv.daimhim.rvdecoration.decoration.base.AbsLinearDecoration;

/**
 * 项目名称：rv.daimhim.rvdecoration
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/11/10 16:08
 * 修改人：Daimhim
 * 修改时间：2017/11/10 16:08
 * 类描述：
 * 修改备注：
 */

public class LinearVerticalDecoration extends AbsLinearDecoration implements RecycleDecoration.DrawBeforeTarget,
        RecycleDecoration.MeasureTarget  {
    /**
     * 画笔
     */
    private Paint mPaint;
    private final int mSize;
    private Rect mRect;
    private boolean isFoot;
    private boolean isHead;
    protected LinearVerticalDecoration(DecorationBuilder.DecorationParams pP) {
        mSize = pP.mContext.getResources().getDimensionPixelSize(pP.verticalSize);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(pP.mContext, pP.verticalColor));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);
        this.isFoot = pP.isFood;
        this.isHead = pP.isHead;
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
        if (lChildAdapterPosition == 0 && isHead) {
            outRect.set(0, mSize, 0, mSize);
        }else if (lChildAdapterPosition == count && !isFoot){
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, mSize);
        }
    }
}
