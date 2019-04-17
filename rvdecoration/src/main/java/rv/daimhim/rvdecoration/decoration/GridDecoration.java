package rv.daimhim.rvdecoration.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import org.daimhim.rvadapter.AdapterManagement;
import org.daimhim.rvadapter.RecyclerViewClick;
import org.daimhim.rvadapter.RecyclerViewEmpty;
import org.daimhim.rvadapter.RecyclerViewExpandable;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.decoration.base.AbsGridDecoration;
import timber.log.Timber;

/**
 * 项目名称：rv.daimhim.rvdecoration
 * 项目版本：RVDecoration
 * 创建时间：2019/3/1 13:45  星期五
 * 创建人：Administrator
 * 修改时间：2019/3/1 13:45  星期五
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class GridDecoration extends AbsGridDecoration {
    private final GridLayoutManager mLayoutManager;
    private final GridLayoutManager.SpanSizeLookup mSpanSizeLookup;
    private final RecyclerView.Adapter mRecyclerViewAdapter;
    /**
     * 画笔
     */
    private Paint mPaint;
    private final int mSize;
    private final int mOrientation;
    private Rect mRect;
    /**
     * Previous Weights
     */
    private int mPreviousWeights = 0;
    /**
     * previous line
     */
    private int mPreviousLine = -1;
    private DecorationBuilder.DecorationParams mDecorationParams;

    public GridDecoration(DecorationBuilder.DecorationParams pParams) {
        mDecorationParams = pParams;
        mSize = pParams.mContext.getResources().getDimensionPixelSize(pParams.verticalSize);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(pParams.mContext, pParams.verticalColor));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);
        mOrientation = pParams.orientation;
        mRect = new Rect();
        mLayoutManager = (GridLayoutManager) pParams.mRecyclerView.getLayoutManager();
        mSpanSizeLookup = mLayoutManager.getSpanSizeLookup();
        mSpanSizeLookup.setSpanIndexCacheEnabled(true);
        mRecyclerViewAdapter = pParams.mRecyclerView.getAdapter();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        int childCount = parent.getChildCount();
//        View childAt;
//        for (int i = 0; i < childCount; i++) {
//            mRect.set(0, 0, 0, 0);
//            childAt = parent.getChildAt(i);
//            getMeasureTarget().getItemOffsets(mRect, childAt, parent, state);
//            DrawHelp.drawLine(c, mRect, mPaint, childAt);
//        }
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
        taskAssignment(outRect, lChildAdapterPosition, lSpanSize, lSpanCount, spanGroupIndexl,
                mRecyclerViewAdapter);
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
        if (pAdapter instanceof RecyclerViewExpandable) {
            expandableGridDecoration(outRect, pChildAdapterPosition, pSpanSize, pSpanCount,
                    pSpanGroupIndexl, (RecyclerViewExpandable) pAdapter);
        } else if (pAdapter instanceof AdapterManagement) {
            AdapterManagement lAdapter1 = (AdapterManagement) pAdapter;
            Pair<Integer, Integer> lIntegerIntegerPair = lAdapter1.indexOfPosition(pChildAdapterPosition);
            RecyclerViewEmpty<RecyclerViewClick.ClickViewHolder> lItem = lAdapter1.getItem(lIntegerIntegerPair.first);
            lAdapter1.modifyBase();
            taskAssignment(outRect, pChildAdapterPosition, pSpanSize, pSpanCount, pSpanGroupIndexl, lItem);
        } else {
            mortalGridDecoration(outRect,pChildAdapterPosition, pSpanSize, pSpanCount, pSpanGroupIndexl);
        }
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
        if (mOrientation == GridLayoutManager.VERTICAL) {
            if (mDecorationParams.isHead && lChildAdapterPositionp < mDecorationParams.baseCount
                    || (mDecorationParams.isFood && lChildAdapterPositionp >= mRecyclerViewAdapter.getItemCount() - mDecorationParams.footCount)) { //group
            } else if (pSpanCount == pSpanSize) { //full line
                outRect.set(mSize, 0, mSize, mSize);
            } else if (mPreviousWeights == 0) { //first
                mPreviousWeights += pSpanSize;
                if (mPreviousLine < pSpanGroupIndexl) {
                    outRect.set(mSize, 0, mSize, mSize);
                } else {
                    outRect.set(0, 0, mSize, mSize);
                }
            } else if (mPreviousWeights + pSpanSize == pSpanCount) { //last
                mPreviousWeights = 0;
                if (mPreviousLine < pSpanGroupIndexl) {
                    outRect.set(0, 0, mSize, mSize);
                } else {
                    outRect.set(mSize, 0, mSize, mSize);
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
        if (mOrientation == GridLayoutManager.VERTICAL) {

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
                    outRect.set(mSize, 0, mSize, mSize);
                } else {
                    outRect.set(0, 0, mSize, mSize);
                }
            } else if (mPreviousWeights + lSpanSizep == lSpanCountp) { //last
                mPreviousWeights = 0;
                if (mPreviousLine <= spanGroupIndexp) {
                    outRect.set(0, 0, mSize, mSize);
                } else {
                    outRect.set(mSize, 0, mSize, mSize);
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


}
