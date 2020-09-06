package rv.daimhim.rvdecoration.decoration.version102;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.decoration.core.AbsGridDecoration;
import timber.log.Timber;

/**
 * 项目名称：rv.daimhim.rvdecoration
 * 项目版本：RVDecoration
 * 创建时间：2019/4/17 15:08  星期三
 * 创建人：Administrator
 * 修改时间：2019/4/17 15:08  星期三
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 纯规则布局
 *
 * @author：Administrator
 */
public class GridVerticalDecoration102 extends AbsGridDecoration {
    /**
     * 画笔
     */
    private final Paint mPaint;
    private final int mSize;
    private final int mFood;
    private final int mHead;
    private final int bothSides;
    private final int mSpanCount;
    private final int mBaseCount;
    private final int mFootCount;


    GridVerticalDecoration102(DecorationBuilder.DecorationParams pParams) {
        bothSides = pParams.mContext.getResources().getDimensionPixelSize(pParams.bothSides);
        mSize = pParams.mContext.getResources().getDimensionPixelSize(pParams.size);
        mFood = pParams.mContext.getResources().getDimensionPixelSize(pParams.food);
        mHead = pParams.mContext.getResources().getDimensionPixelSize(pParams.head);
        mBaseCount = pParams.baseCount;
        mFootCount = pParams.footCount;
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(pParams.mContext, pParams.color));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);
        mSpanCount = ((GridLayoutManager) pParams.mRecyclerView.getLayoutManager()).getSpanCount();
        Timber.i("bothSides %s mSize %s mFood %s mHead %s",bothSides,
                mSize,
                mFood,
                mHead);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int lChildAdapterPosition = parent.getChildAdapterPosition(view); //当前位置
        int count = state.getItemCount(); //总长度
        if (mBaseCount > lChildAdapterPosition
                || lChildAdapterPosition > count - mFootCount){
            return;
        }
        lChildAdapterPosition -= mBaseCount;
        if (mSpanCount > lChildAdapterPosition){
            outRect.top = mHead;
        }
        if (lChildAdapterPosition % mSpanCount == 0){
            outRect.left = bothSides;
        }
        outRect.right = mSize;
        if (lChildAdapterPosition % mSpanCount == mSpanCount-1){
            outRect.right = bothSides;
        }
        outRect.bottom = mSize;
        if (mSpanCount > count || lChildAdapterPosition/mSpanCount == count/mSpanCount){
            outRect.bottom = mFood;
        }
    }

}
