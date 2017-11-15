package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;

/**
 *
 * @author Daimhim
 * @date 2017/2/14
 */

public abstract class RecycleDecoration extends RecyclerView.ItemDecoration {
    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 方向 垂直、水平、十字
     */
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_CROSS = Integer.MIN_VALUE;

    public static final int HORIZONTAL_CROSS = Integer.MAX_VALUE;

    private int mOrientation = VERTICAL;  //方向

    /**
     * 默认间距高度
     */
    private int mDividerWidth = 2;//分割线高度，默认为2px

    /**
     * 默认间距颜色
     */
    private int mDividerColor = android.R.color.white;

    private CustomizedOffsets mCustomizedOffsets;

    private CustomizedDraw mCustomizedDraw;

    private SparseArray<CacheItemView> mItemViewSparseArray;

    private Context mContext;

    private boolean leftLine;
    private boolean topLine;
    private boolean rightLine;
    private boolean bottomLine;

    private int leftRect;
    private int topRect;
    private int rightRect;
    private int bottomRect;
    @ColorRes
    private int leftRectColor;
    @ColorRes
    private int topRectColor;
    @ColorRes
    private int rightRectColor;
    @ColorRes
    private int bottomRectColor;

    protected GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    @Deprecated
    public RecycleDecoration() {
    }

    public RecycleDecoration(Context context) {
        mContext = context;
    }

    protected RecycleDecoration create(DecorationBuilder decorationBuilder) {
        if (null != decorationBuilder.mContext) {
            this.mContext = decorationBuilder.mContext;
        }
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mDividerColor = decorationBuilder.mDividerColor;
        this.mPaint.setColor(ContextCompat.getColor(mContext, decorationBuilder.mDividerColor));
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mOrientation = decorationBuilder.mOrientation;
        this.mDividerWidth = decorationBuilder.mDividerWidth;
        this.mCustomizedOffsets = decorationBuilder.mCustomizedOffsets;
        this.mCustomizedDraw = decorationBuilder.mCustomizedDraw;
        this.leftLine = decorationBuilder.leftLine;
        this.topLine = decorationBuilder.topLine;
        this.rightLine = decorationBuilder.rightLine;
        this.bottomLine = decorationBuilder.bottomLine;
        this.leftRect = decorationBuilder.leftRect;
        this.topRect = decorationBuilder.topRect;
        this.rightRect = decorationBuilder.rightRect;
        this.bottomRect = decorationBuilder.bottomRect;
        this.leftRectColor = decorationBuilder.leftRectColor;
        this.topRectColor = decorationBuilder.topRectColor;
        this.rightRectColor = decorationBuilder.rightRectColor;
        this.bottomRectColor = decorationBuilder.bottomRectColor;
        mItemViewSparseArray = new SparseArray<>();
        return this;
    }


    /**
     * 在绘制完Item后调用
     *
     * @param c      画布
     * @param parent 缓存RecyclerView
     * @param state  RecyclerView状态
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(childAt);
            //绘制边线 获取用户定义的绘制类
            CacheItemView itemOffsets = mItemViewSparseArray.get(position);
            if (null == itemOffsets){break;}
            if (itemOffsets.outRect == null){break;}
            if (itemOffsets.outRectColor == null){break;}
            if (null != mCustomizedDraw) {
                if (!mCustomizedDraw.obtainDrawOver(c, position, childAt,
                        itemOffsets.outRect,
                        itemOffsets.outRectColor, getDividerColor(), getDividerWidth())) {

                    drawLine(c, itemOffsets.outRect,
                            itemOffsets.outRectColor, childAt);
                }
            } else {
                drawLine(c, itemOffsets.outRect,
                        itemOffsets.outRectColor, childAt);
            }
        }
    }

    /**
     * 绘制类 根据缓存对象mViewHashMap中的数据 去绘制
     *
     * @param c            画布
     * @param outRect      相对于Item四边的边距
     * @param outRectColor 相对于四边的边距的颜色
     * @param childAt      当前Item的View对象
     */
    private void drawLine(Canvas c, Rect outRect, Rect outRectColor, View childAt) {
        int startX;
        int startY;
        int stopX;
        int stopY;
        if (outRect.left != 0) {
            startX = childAt.getLeft() - (outRect.left / 2);
            startY = childAt.getTop() - outRect.top;
            stopX = childAt.getLeft() - (outRect.left / 2);
            stopY = childAt.getBottom() + outRect.bottom;
            if (outRectColor.left != 0) {
                mPaint.setColor(ContextCompat.getColor(mContext, outRectColor.left));
            }
            mPaint.setStrokeWidth(outRect.left);
            c.drawLine(startX, startY, stopX, stopY, mPaint);
        }
        if (outRect.right != 0) {
            startX = childAt.getRight() + (outRect.right / 2);
            startY = childAt.getTop() - outRect.top;
            stopX = childAt.getRight() + (outRect.right / 2);
            stopY = childAt.getBottom() + outRect.bottom;
            if (outRectColor.right != 0) {
                mPaint.setColor(ContextCompat.getColor(mContext, outRectColor.right));
            }
            mPaint.setStrokeWidth(outRect.right);
            c.drawLine(startX, startY, stopX, stopY, mPaint);
        }
        if (outRect.top != 0) {
            startX = childAt.getLeft() - outRect.left;
            startY = childAt.getTop() - (outRect.top / 2);
            stopX = childAt.getRight() + outRect.right;
            stopY = childAt.getTop() - (outRect.top / 2);
            if (outRectColor.top != 0) {
                mPaint.setColor(ContextCompat.getColor(mContext, outRectColor.top));
            }
            mPaint.setStrokeWidth(outRect.top);
            c.drawLine(startX, startY, stopX, stopY, mPaint);
        }
        if (outRect.bottom != 0) {
            startX = childAt.getLeft() - outRect.left;
            startY = childAt.getBottom() + (outRect.bottom / 2);
            stopX = childAt.getRight() + outRect.right;
            stopY = childAt.getBottom() + (outRect.bottom / 2);
            if (outRectColor.bottom != 0) {
                mPaint.setColor(ContextCompat.getColor(mContext, outRectColor.bottom));
            }
            mPaint.setStrokeWidth(outRect.bottom);
            c.drawLine(startX, startY, stopX, stopY, mPaint);
        }
    }

    /**
     * 获取每个Item对象的四边边距
     *
     * @param outRect 设置Item四边边距的对象
     * @param view    当前Item的View对象
     * @param parent  缓存RecyclerView
     * @param state   RecyclerView状态
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int count = state.getItemCount() - 1; //总长度
        int position = parent.getChildAdapterPosition(view); //当前位置
        int cacheSize = parent.getChildCount();  //缓存长度
        CacheItemView itemOffsets = new CacheItemView();
        Rect itemRect = new Rect();
        Rect itemColor = new Rect();
        if (null != mCustomizedOffsets) {
            if (!mCustomizedOffsets.obtainOffsets(itemRect, itemColor, getDividerColor(), getDividerWidth(), position)) {
                getDefaultOffsets(itemRect, itemColor, view, parent, state);
            }
        } else {
            getDefaultOffsets(itemRect, itemColor, view, parent, state);
        }

        outRect.set(itemRect.left, itemRect.top, itemRect.right, itemRect.bottom);

        itemOffsets.position = position;
        itemOffsets.outRectColor = itemColor;
        itemOffsets.outRect = itemRect;
        mItemViewSparseArray.put(position,itemOffsets);
    }

    /**
     * 任务分发抽象方法 把边距测量、设置等任务分发给子类
     *
     * @param outRect   设置Item四边边距的对象 该对象是自定义缓存中的对象
     * @param itemColor 设置Item四边颜色的对象 该对象是自定义缓存中的对象
     * @param view      当前Item的View对象
     * @param parent    缓存RecyclerView
     * @param state     RecyclerView状态
     */
    protected abstract void getDefaultOffsets(Rect outRect, Rect itemColor, View view, RecyclerView parent, RecyclerView.State state);

    /**
     * 缓存每条Item边距、颜色、位置
     */
    class CacheItemView {
        int position;
        Rect outRect = new Rect();
        Rect outRectColor = new Rect();

        @Override
        public String toString() {
            return "CacheItemView{" +
                    "position=" + position +
                    ", outRect=" + outRect.left + ":" + outRect.top + ":" + outRect.right + ":" + outRect.bottom +
                    ", outRectColor=" + outRectColor.left + ":" + outRectColor.top + ":" + outRectColor.right + ":" + outRectColor.bottom +
                    '}';
        }
    }

    /**
     * 该接口用于测量边距、设置颜色 实现可控制默认实现
     */
    public interface CustomizedOffsets {
        public boolean obtainOffsets(Rect itemRect, Rect itemColor, int dividerColor, int dividerWidth, int position);
    }

    /**
     * 该接口用于绘制边距、绘制颜色 实现可控制默认实现
     */
    public interface CustomizedDraw {
        public boolean obtainDrawOver(Canvas c, int position, View childAt, Rect outRect, Rect RectColor, int dividerColor, int dividerWidth);
    }

    /**
     * 设置顶部 不同布局 不同体现
     *
     * @param outRect   顶部边距
     * @param itemColor 顶部颜色
     */
    protected void drawTop(Rect outRect, Rect itemColor) {
        if (isTopLine() && 0 != getTopRect() && 0 != getTopRectColor()) {
            outRect.top = getTopRect();
            itemColor.top = getTopRectColor();
        } else if (isTopLine() && 0 != getTopRect()) {
            outRect.top = getTopRect();
            itemColor.top = getDividerColor();
        } else if (isTopLine() && 0 != getTopRectColor()) {
            outRect.top = getDividerWidth();
            itemColor.top = getTopRectColor();
        } else if (isTopLine()) {
            outRect.top = getDividerWidth();
            itemColor.top = getDividerColor();
        }
    }

    /**
     * 设置左边 不同布局 不同体现
     *
     * @param outRect   左边边距
     * @param itemColor 左边颜色
     */
    protected void drawLeft(Rect outRect, Rect itemColor) {
        if (isLeftLine() && 0 != getLeftRect() && 0 != getLeftRectColor()) {
            outRect.left = getLeftRect();
            itemColor.left = getLeftRectColor();
        } else if (isLeftLine() && 0 != getLeftRect()) {
            outRect.left = getLeftRect();
            itemColor.left = getDividerColor();
        } else if (isLeftLine() && 0 != getLeftRectColor()) {
            outRect.left = getDividerWidth();
            itemColor.left = getLeftRectColor();
        } else if (isLeftLine()) {
            outRect.left = getDividerWidth();
            itemColor.left = getDividerColor();
        }
    }

    /**
     * 设置右边 不同布局 不同体现
     *
     * @param outRect   右边边距
     * @param itemColor 右边颜色
     */
    protected void drawRight(Rect outRect, Rect itemColor) {
        if (isRightLine() && 0 != getRightRect() && 0 != getRightRectColor()) {
            outRect.right = getRightRect();
            itemColor.right = getRightRectColor();
        } else if (isRightLine() && 0 != getRightRect()) {
            outRect.right = getRightRect();
            itemColor.right = getDividerColor();
        } else if (isRightLine() && 0 != getRightRectColor()) {
            outRect.right = getDividerWidth();
            itemColor.right = getRightRectColor();
        } else if (isRightLine()) {
            outRect.right = getDividerWidth();
            itemColor.right = getDividerColor();
        }
    }

    /**
     * 设置底部 不同布局 不同体现
     *
     * @param outRect   底部边距
     * @param itemColor 底部颜色
     */
    protected void drawBottom(Rect outRect, Rect itemColor) {
        if (isBottomLine() && 0 != getBottomRect() && 0 != getBottomRectColor()) {
            outRect.bottom = getBottomRect();
            itemColor.bottom = getBottomRectColor();
        } else if (isBottomLine() && 0 != getBottomRect()) {
            outRect.bottom = getBottomRect();
            itemColor.bottom = getDividerColor();
        } else if (isBottomLine() && 0 != getBottomRectColor()) {
            outRect.bottom = getDividerWidth();
            itemColor.bottom = getBottomRectColor();
        } else if (isBottomLine()) {
            outRect.bottom = getDividerWidth();
            itemColor.bottom = getDividerColor();
        }
    }

    /**
     * 获取每行SpanCount  根据不同对象获取 可重写
     *
     * @param parent 当前RecyclerView对象
     * @return 获取每行SpanCount
     */
    protected int getSpanCount(RecyclerView parent) {
        int spanCount = 0;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            spanCount = gridLayoutManager.getSpanCount();
            mSpanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        } else if (layoutManager instanceof LinearLayoutManager) {
            spanCount = 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public int getDividerWidth() {
        return mDividerWidth;
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    public CustomizedOffsets getCustomizedOffsets() {
        return mCustomizedOffsets;
    }

    public CustomizedDraw getCustomizedDraw() {
        return mCustomizedDraw;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isLeftLine() {
        return leftLine;
    }

    public boolean isTopLine() {
        return topLine;
    }

    public boolean isRightLine() {
        return rightLine;
    }

    public boolean isBottomLine() {
        return bottomLine;
    }

    public int getLeftRect() {
        return leftRect;
    }

    public int getTopRect() {
        return topRect;
    }

    public int getRightRect() {
        return rightRect;
    }

    public int getBottomRect() {
        return bottomRect;
    }

    public int getLeftRectColor() {
        return leftRectColor;
    }

    public int getTopRectColor() {
        return topRectColor;
    }

    public int getRightRectColor() {
        return rightRectColor;
    }

    public int getBottomRectColor() {
        return bottomRectColor;
    }
}
