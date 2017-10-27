package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 使用方法 请看DecorationBuilder类
 * 该类用于StaggeredGridLayoutManager
 * Created by Daimhim on 2017/2/16.
 */

public class StaggeredGridDecoration extends RecycleDecoration {

    @Deprecated
    public StaggeredGridDecoration() {
    }

    public StaggeredGridDecoration(Context context) {
        super(context);
    }

    private int mPosition;
    private int mCount;
    private int mSpanCount;

    @Override
    protected void getDefaultOffsets(Rect outRect, Rect itemColor, View view, RecyclerView parent,
                                     RecyclerView.State state) {
        mCount = state.getItemCount(); //总长度
        mPosition = parent.getChildAdapterPosition(view); //当前位置
        mSpanCount = getSpanCount(parent);

        switch (getOrientation()) {
            case VERTICAL:
                arrangementVertical(outRect, itemColor, parent);
                break;
            case HORIZONTAL:
                arrangementHorizontal(outRect, itemColor, parent);
                break;
        }

    }

    private void arrangementVertical(Rect outRect, Rect itemColor, RecyclerView parent) {
        //除了第一行，其他行都画顶部线
        if (!isTopLine()) {
            drawTop(outRect, itemColor);
        }

        if (isLeftLine()) {
            //不是第一列
            if (mPosition % mSpanCount != 0) {
                drawLeft(outRect, itemColor);
            }
        }

        if (isRightLine()) {
            if (!isLastColum(parent, mPosition, mSpanCount, mCount)) {
                drawRight(outRect, itemColor);
            }
        }

        if (isBottomLine()) {
            if (!isLastRaw(parent, mPosition, mSpanCount, mCount)) {
                drawBottom(outRect, itemColor);
            }
        }
    }

    private void arrangementHorizontal(Rect outRect, Rect itemColor, RecyclerView parent) {
        //除了第一行，其他行都画顶部线
        if (!isTopLine()) {
            drawTop(outRect, itemColor);
        }

        if (isLeftLine()) {
            //不是第一列
            if (mPosition >= mSpanCount) {
                drawLeft(outRect, itemColor);
            }
        }

        if (isRightLine()) {
//            if (mPosition <= mCount - mSpanCount - (mSpanCount - 1 - (mCount - 1) % mSpanCount)) {
//                drawRight(outRect, itemColor);
//            }
            if (!isLastColum(parent, mPosition, mSpanCount, mCount)) {
                drawRight(outRect, itemColor);
            }
        }

        if (isBottomLine()) {
            if (!isLastRaw(parent, mPosition, mSpanCount, mCount)) {
                drawBottom(outRect, itemColor);
            }
        }
    }


    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列，则不需要绘制右边
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一列，则不需要绘制右边
                if (pos >= childCount - mSpanCount)
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount - mSpanCount)
                    return true;
            } else {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
