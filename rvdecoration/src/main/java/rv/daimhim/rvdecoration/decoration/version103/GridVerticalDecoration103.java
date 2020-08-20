package rv.daimhim.rvdecoration.decoration.version103;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.daimhim.rvadapter.RecyclerViewExpandable;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.decoration.base.AbsGridDecoration;
import timber.log.Timber;

/**
 * 项目名称：rv.daimhim.rvdecoration
 * 项目版本：RVDecoration
 * 创建时间：2019/4/17 15:08  星期三
 * 创建人：Administrator
 * 修改时间：2019/4/17 15:08  星期三
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class GridVerticalDecoration103 extends AbsGridDecoration {
    private final GridLayoutManager mLayoutManager;
    private final GridLayoutManager.SpanSizeLookup mSpanSizeLookup;
    private final RecyclerView.Adapter mRecyclerViewAdapter;
    /**
     * 画笔
     */
    private Paint mPaint;
    private final int mSize;
    private final int mFood;
    private final int mHead;
    private final int bothSides;
    /**
     * Previous Weights
     */
    private int mPreviousWeights = 0;
    /**
     * previous line
     */
    private int mPreviousLine = -1;
    private DecorationBuilder.DecorationParams mDecorationParams;

    GridVerticalDecoration103(DecorationBuilder.DecorationParams pParams) {
        mDecorationParams = pParams;
        bothSides = pParams.mContext.getResources().getDimensionPixelSize(pParams.bothSides);
        mSize = pParams.mContext.getResources().getDimensionPixelSize(pParams.size);
        mFood = pParams.mContext.getResources().getDimensionPixelSize(pParams.food);
        mHead = pParams.mContext.getResources().getDimensionPixelSize(pParams.head);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(pParams.mContext, pParams.color));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);
        mLayoutManager = (GridLayoutManager) pParams.mRecyclerView.getLayoutManager();
        mSpanSizeLookup = mLayoutManager.getSpanSizeLookup();
        mSpanSizeLookup.setSpanIndexCacheEnabled(true);
        mRecyclerViewAdapter = pParams.mRecyclerView.getAdapter();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int lChildAdapterPosition = parent.getChildAdapterPosition(view); //当前位置
        int lSpanSize = mSpanSizeLookup.getSpanSize(lChildAdapterPosition); // 当前Item所占权重
        int lSpanCount = mLayoutManager.getSpanCount();  //一行总权重
        int spanGroupIndexl = mSpanSizeLookup.getSpanGroupIndex(lChildAdapterPosition, lSpanCount);
        if (lChildAdapterPosition == 0) {
            mPreviousWeights = 0;
            mPreviousLine = -1;
        }
//        mortalGridDecoration(outRect,lChildAdapterPosition,lSpanSize,lSpanCount,);
    }

    /**
     * 任务派发
     *
     * @param outRect
     * @param pChildAdapterPosition
     * @param pSpanSize
     * @param pSpanCount
     * @param pSpanGroupIndexl
     * @param pAdapter
     */
    private void taskAssignment(Rect outRect, int pChildAdapterPosition, int pSpanSize, int pSpanCount,
                                int pSpanGroupIndexl, RecyclerView.Adapter pAdapter) {
        Timber.d("pChildAdapterPosition:%s pSpanSize:%s pSpanCount:%s pSpanGroupIndexl:%s pAdapter:%s",
                pChildAdapterPosition, pSpanSize, pSpanCount, pSpanGroupIndexl, pAdapter.getClass().getSimpleName());

    }

    /**
     * 规则布局
     *
     * @param outRect
     * @param pSpanSize
     * @param pSpanCount
     * @param pSpanGroupIndexl
     */
    private void mortalGridDecoration(Rect outRect, int lChildAdapterPositionp, int pSpanSize, int pSpanCount, int pSpanGroupIndexl) {
            if (lChildAdapterPositionp < mDecorationParams.baseCount
                    || (lChildAdapterPositionp >= mRecyclerViewAdapter.getItemCount() - mDecorationParams.footCount)) { //group
            } else if (pSpanCount == pSpanSize) { //full line
                outRect.set(mSize, 0, mSize, mSize);
            } else if (mPreviousWeights == 0) { //first
                mPreviousWeights += pSpanSize;
                if (mPreviousLine < pSpanGroupIndexl) {
                    outRect.set(bothSides, 0, mSize, mSize);
                } else {
                    outRect.set(0, 0, bothSides, mSize);
                }
            } else if (mPreviousWeights + pSpanSize == pSpanCount) { //last
                mPreviousWeights = 0;
                if (mPreviousLine < pSpanGroupIndexl) {
                    outRect.set(0, 0, bothSides, mSize);
                } else {
                    outRect.set(bothSides, 0, mSize, mSize);
                }
                mPreviousLine = pSpanGroupIndexl;
            } else {  // center
                mPreviousWeights += pSpanSize;
                if (mPreviousLine < pSpanGroupIndexl) {
                    outRect.set(0, 0, mSize, mSize);
                } else {
                    outRect.set(0, 0, mSize, mSize);
                }
            }
    }

    /**
     * 不规则布局
     *
     * @param outRect
     * @param lChildAdapterPositionp
     * @param lSpanSizep
     * @param lSpanCountp
     * @param spanGroupIndexp
     * @param pRecyclerViewExpandable
     */
    private void expandableGridDecoration(Rect outRect, int lChildAdapterPositionp, int lSpanSizep,
                                          int lSpanCountp, int spanGroupIndexp, RecyclerViewExpandable pRecyclerViewExpandable) {
            Pair<Integer, Integer> lPair = pRecyclerViewExpandable
                    .indexOfPosition(lChildAdapterPositionp - (mDecorationParams.baseCount == -1 ?
                            pRecyclerViewExpandable.getBaseCount() : mDecorationParams.baseCount));
            if (lPair.second == -1
                    || (mDecorationParams.isHead && (lChildAdapterPositionp < mDecorationParams.baseCount || lChildAdapterPositionp < pRecyclerViewExpandable.getBaseCount()))
                    || (mDecorationParams.isFood && lChildAdapterPositionp > mRecyclerViewAdapter.getItemCount() - mDecorationParams.footCount)) { //group
            } else if (lSpanCountp == lSpanSizep) { //full line
                outRect.set(mSize, 0, mSize, mSize);
            } else if (mPreviousWeights == 0) { //first
                mPreviousWeights += lSpanSizep;
                if (mPreviousLine <= spanGroupIndexp) {
                    outRect.set(mDecorationParams.bothSides, 0, mSize, mSize);
                } else {
                    outRect.set(0, 0, mDecorationParams.bothSides, mSize);
                }
            } else if (mPreviousWeights + lSpanSizep == lSpanCountp) { //last
                mPreviousWeights = 0;
                if (mPreviousLine <= spanGroupIndexp) {
                    outRect.set(0, 0, mDecorationParams.bothSides, mSize);
                } else {
                    outRect.set(mDecorationParams.bothSides, 0, mSize, mSize);
                }
                mPreviousLine = spanGroupIndexp;
            } else {  // center
                mPreviousWeights += lSpanSizep;
                if (mPreviousLine <= spanGroupIndexp) {
                    outRect.set(0, 0, mSize, mSize);
                } else {
                    outRect.set(0, 0, mSize, mSize);
                }
            }
    }
}
