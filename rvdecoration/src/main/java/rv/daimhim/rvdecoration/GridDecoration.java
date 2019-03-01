package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import org.daimhim.rvadapter.RecyclerViewExpandable;

import java.util.Timer;

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
public class GridDecoration implements RecycleDecoration.DrawBeforeTarget {
    private final GridLayoutManager mLayoutManager;
    private final GridLayoutManager.SpanSizeLookup mSpanSizeLookup;
    /**
     * 画笔
     */
    private Paint mPaint;
    private final int mSize;
    private final int mOrientation;
    private Rect mRect;
    private int mPreviousPosition = 0;
    private RecycleDecoration.MeasureTarget mMeasureTarget;

    public GridDecoration(Context pContext, @ColorRes int color,
                          @DimenRes int size, int orientation, RecyclerView pRecyclerView) {
        mSize = pContext.getResources().getDimensionPixelSize(size);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(pContext, color));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(mSize);
        mOrientation = orientation;
        mRect = new Rect();
        mLayoutManager = (GridLayoutManager) pRecyclerView.getLayoutManager();
        mSpanSizeLookup = mLayoutManager.getSpanSizeLookup();
        mSpanSizeLookup.setSpanIndexCacheEnabled(true);
        RecyclerView.Adapter lAdapter = pRecyclerView.getAdapter();
        if (lAdapter instanceof RecyclerViewExpandable) {
            mMeasureTarget = new ExpandableVerticalGridDecoration((RecyclerViewExpandable) lAdapter);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        View childAt;
        for (int i = 0; i < childCount; i++) {
            mRect.set(0, 0, 0, 0);
            childAt = parent.getChildAt(i);
            getMeasureTarget().getItemOffsets(mRect, childAt, parent, state);
            DrawHelp.drawLine(c, mRect, mPaint, childAt);
        }
    }


    public RecycleDecoration.MeasureTarget getMeasureTarget() {
        return mMeasureTarget;
    }

    class ExpandableVerticalGridDecoration implements RecycleDecoration.MeasureTarget {
        private RecyclerViewExpandable mRecyclerViewExpandable;

        public ExpandableVerticalGridDecoration(RecyclerViewExpandable pRecyclerViewExpandable) {
            mRecyclerViewExpandable = pRecyclerViewExpandable;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int lChildAdapterPosition = parent.getChildAdapterPosition(view); //当前位置
            int lSpanSize = mSpanSizeLookup.getSpanSize(lChildAdapterPosition); // 当前Item所占权重
            int lSpanCount = mLayoutManager.getSpanCount();  //一行总权重
//            Timber.i("lChildAdapterPosition:%s mPreviousPosition:%s lSpanCount:%s lSpanSize:%s",lChildAdapterPosition,mPreviousPosition,lSpanCount,lSpanSize);
            if (mOrientation == GridLayoutManager.VERTICAL) {
                Pair<Integer, Integer> lPair = mRecyclerViewExpandable.indexOfPosition(lChildAdapterPosition);
//                Timber.i("first:%s second:%s position:%s lSpanSize:%s",
//                        lPair.first,lPair.second,lChildAdapterPosition,lSpanSize);
                if (lPair.second == -1) { //group
                    onMeasureItemOffsets(lChildAdapterPosition, 0, outRect, true);
                } else if (lSpanCount == lSpanSize){ //full line
                    onMeasureItemOffsets(lChildAdapterPosition, -1, outRect, false);
                }else if (mPreviousPosition == 0) { //first
                    mPreviousPosition += lSpanSize;
                    onMeasureItemOffsets(lChildAdapterPosition, -1, outRect, false);
                } else if (mPreviousPosition + lSpanSize == lSpanCount) { //last
                    mPreviousPosition = 0;
                    onMeasureItemOffsets(lChildAdapterPosition, 1, outRect, false);
                } else {  // center
                    mPreviousPosition += lSpanSize;
                    onMeasureItemOffsets(lChildAdapterPosition, 0, outRect, false);
                }
            }
        }
    }


    /**
     * @param position
     * @param pPlace   -1 lift 0 cent 1 right
     * @param outRect
     */
    void onMeasureItemOffsets(int position, int pPlace, Rect outRect, boolean isFull) {
        Timber.i("position:%s pPlace%s",position,pPlace);
        if (isFull) {
//            outRect.set(0, 0, 0, 0);
        } else if (pPlace == 1) {
            outRect.set(0, 0, mSize, mSize);
        } else if (pPlace == -1) {
            outRect.set(mSize, 0, mSize, mSize);
        } else {
            outRect.set(0, 0, mSize, mSize);
        }
    }

}
