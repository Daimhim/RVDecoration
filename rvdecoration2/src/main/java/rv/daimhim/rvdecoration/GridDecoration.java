package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.reflect.Field;

/**
 * 使用方法 请看DecorationBuilder类
 * 该类用于GridLayoutManager
 * Created by Daimhim on 2017/2/16.
 */

public class GridDecoration extends RecycleDecoration {

    private int mPosition = 0;  //所在位置
    private int mCurrentRow = -1; //所在行数
    private int mRow = -1;
    private int mColumn = -1;

    @Deprecated
    public GridDecoration() {
    }

    public GridDecoration(Context context) {
        super(context);
    }

    /**
     * 设置用户自定义的边线
     *
     * @param outRect   设置Item四边边距的对象 该对象是自定义缓存中的对象
     * @param itemColor 设置Item四边颜色的对象 该对象是自定义缓存中的对象
     * @param view      当前Item的View对象
     * @param parent    缓存RecyclerView
     * @param state     RecyclerView状态
     */
    @Override
    protected void getDefaultOffsets(Rect outRect, Rect itemColor, View view, RecyclerView parent,
                                     RecyclerView.State state) {
        if (mRow == -1 || mColumn == -1) {
            getRowsAndColumns(parent, state);
        }
        int currentRow = getOrientation() == VERTICAL_CROSS || getOrientation() == VERTICAL ? mRow : mColumn;
        int indexes = parent.getChildAdapterPosition(view);   //当前位置
        int spanCount = getSpanCount(parent);   //一共有几行/几列
        int count = state.getItemCount();  //总长度

        currentRow = currentRow == 1 ? count / spanCount : currentRow;

        int spanSize = mSpanSizeLookup.getSpanSize(indexes); //当前item所占跨度
        int spanIndex = mSpanSizeLookup.getSpanIndex(indexes, spanCount);  //返回所提供位置的最后跨度索引
        int groupIndex = mSpanSizeLookup.getSpanGroupIndex(indexes, spanCount);//当前行数

        if (mCurrentRow == groupIndex) { //老一行
            mPosition = mPosition + spanSize;
            if (mCurrentRow == 0 && (mCurrentRow == currentRow || count == spanCount)) {  //第一行 也是最后一行
                if (mPosition == spanCount && indexes == count - 1) {  //最后一条item
                    arrangementTopAndLast(outRect, itemColor, getOrientation());
                } else {  //中间Item
                    arrangementTopAndBottom(outRect, itemColor, getOrientation());
                }
            } else if (mCurrentRow == 0) { //第一行
                if (mPosition == spanCount) { //最后一个Item 右上
                    arrangementRightAndTop(outRect, itemColor, getOrientation());
                } else {   //顶部Item
                    arrangementTop(outRect, itemColor, getOrientation());
                }
            } else if (mCurrentRow + 1 == currentRow && count - indexes <= spanCount) { //最后一行
                if (mPosition <= spanCount && indexes + spanSize == count) { //右下Item
                    arrangementRightAndBottom(outRect, itemColor, getOrientation());
                } else {  //底部Item
                    arrangementBottom(outRect, itemColor, getOrientation());
                }
            } else {  //中间行
                if (mPosition == spanCount) {  //右边Item
                    arrangementRight(outRect, itemColor, getOrientation());  //右边
                } else {   //普通Item
                    arrangementContent(outRect, itemColor, getOrientation()); //中间行
                }
            }
        } else {   //新一行
            mCurrentRow = groupIndex;
            mPosition = spanSize;
            if (mCurrentRow == 0 && (mCurrentRow == currentRow || count <= spanCount)) {  //第一行 也是最后一行
                if (indexes == 0 && indexes == count - 1) { //第一个也是最后一个
                    arrangementFirstAndLast(outRect, itemColor, getOrientation());
                } else {
                    arrangementFirstAndTopAndBottom(outRect, itemColor, getOrientation());
                }
            } else if (mCurrentRow == 0) { //第一行
                if (indexes == 0) {
                    arrangementLeftAndTop(outRect, itemColor, getOrientation());
                }
            } else if (mCurrentRow + 1 == currentRow && count - indexes <= spanCount) { //最后一行
                if (indexes + spanSize == count) {  //左边 右边 下边
                    arrangementLeftAndRightAndBottom(outRect, itemColor, getOrientation());
                } else {  //左下
                    arrangementLeftAndBottom(outRect, itemColor, getOrientation());
                }
            } else {  //中间行
                arrangementLeft(outRect, itemColor, getOrientation());
            }
        }
    }

    /**
     * 第一行 也是最后一行 但不是最后一个的 第一个Item
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementFirstAndTopAndBottom(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
            case VERTICAL:
                drawTop(outRect, itemColor);
                drawLeft(outRect, itemColor);
                drawBottom(outRect, itemColor);
                break;
            case HORIZONTAL_CROSS:
            case HORIZONTAL:
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                drawLeft(outRect, itemColor);
                drawTop(outRect, itemColor);
                break;
        }
    }

    /**
     * 第一行 也是最后一行 中间的Item
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementTopAndBottom(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
            case VERTICAL:
                drawTop(outRect, itemColor);
                drawBottom(outRect, itemColor);
                break;
            case HORIZONTAL_CROSS:
            case HORIZONTAL:
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                drawLeft(outRect, itemColor);
                break;
        }
    }

    /**
     * 第一行 也是最后一行 最后一个Item
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementTopAndLast(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
            case VERTICAL:
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                drawTop(outRect, itemColor);
                break;
            case HORIZONTAL_CROSS:
            case HORIZONTAL:
                drawBottom(outRect, itemColor);
                drawRight(outRect, itemColor);
                drawLeft(outRect, itemColor);
                break;
        }
    }

    /**
     * 第一行 也是最后一行 只有一个Item时
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementFirstAndLast(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
            case VERTICAL:
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                drawTop(outRect, itemColor);
                drawLeft(outRect, itemColor);
                break;
            case HORIZONTAL_CROSS:
            case HORIZONTAL:
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                drawLeft(outRect, itemColor);
                drawTop(outRect, itemColor);
                break;
        }
    }

    /**
     * 最后一行的 第一个 也是最后一行的 最后一个
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementLeftAndRightAndBottom(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
            case VERTICAL:
                drawLeft(outRect, itemColor);
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                break;
            case HORIZONTAL_CROSS:
            case HORIZONTAL:
                drawRight(outRect, itemColor);
                drawTop(outRect, itemColor);
                drawBottom(outRect, itemColor);
                break;
        }
    }

    /**
     * 最后一行的最后一个Item
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementRightAndBottom(Rect outRect, Rect itemColor, int orientation) {

        switch (orientation) {
            case VERTICAL_CROSS:
            case VERTICAL:
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                break;
            case HORIZONTAL_CROSS:
            case HORIZONTAL:
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                break;
            default:
                break;
        }
    }

    /**
     * 最后一行的 第一个 但不是最后一行的最后一个
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementLeftAndBottom(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
            case VERTICAL:
                drawLeft(outRect, itemColor);
                drawBottom(outRect, itemColor);
                break;
            case HORIZONTAL_CROSS:
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
            case HORIZONTAL:
                drawTop(outRect, itemColor);
                drawRight(outRect, itemColor);
                break;
            default:
                break;
        }
    }

    /**
     * 第一行的最后一个Item  但不是数据的最后
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementRightAndTop(Rect outRect, Rect itemColor, int orientation) {

        switch (orientation) {
            case VERTICAL_CROSS:
            case VERTICAL:
                drawTop(outRect, itemColor);
                drawRight(outRect, itemColor);
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
                break;
            case HORIZONTAL_CROSS:
            case HORIZONTAL:
                drawLeft(outRect, itemColor);
                drawBottom(outRect, itemColor);
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
                break;
            default:
                break;
        }
    }

    /**
     * 第一行的第一个Item 不止一条数据
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementLeftAndTop(Rect outRect, Rect itemColor, int orientation) {

        switch (orientation) {
            case VERTICAL_CROSS:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
            case VERTICAL:
                drawTop(outRect, itemColor);
                drawLeft(outRect, itemColor);
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
                break;
            case HORIZONTAL_CROSS:
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
            case HORIZONTAL:
                drawLeft(outRect, itemColor);
                drawTop(outRect, itemColor);
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
                break;
        }
    }

    /**
     * 中间的普通Item
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementContent(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
            case VERTICAL:
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
                break;
            case HORIZONTAL_CROSS:
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
            case HORIZONTAL:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
                break;
        }
    }

    /**
     * 最后一行的 中间Item
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementBottom(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
            case VERTICAL:
                drawBottom(outRect, itemColor);
                break;
            case HORIZONTAL_CROSS:
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
            case HORIZONTAL:
                drawRight(outRect, itemColor);
                break;
        }
    }

    /**
     * 中间行的 最后一条
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementRight(Rect outRect, Rect itemColor, int orientation) {

        switch (orientation) {
            case VERTICAL_CROSS:
            case VERTICAL:
                drawRight(outRect, itemColor);
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
                break;
            case HORIZONTAL_CROSS:
            case HORIZONTAL:
                drawBottom(outRect, itemColor);
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
                break;
        }
    }

    /**
     * 中间行的 第一行
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementLeft(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL_CROSS:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
            case VERTICAL:
                drawLeft(outRect, itemColor);
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
                break;
            case HORIZONTAL_CROSS:
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
            case HORIZONTAL:
                drawTop(outRect, itemColor);
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
                break;

        }
    }

    /**
     * 第一行的 顶部中间行
     *
     * @param outRect
     * @param itemColor
     * @param orientation
     */
    private void arrangementTop(Rect outRect, Rect itemColor, int orientation) {

        switch (orientation) {
            case VERTICAL_CROSS:
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
            case VERTICAL:
                drawTop(outRect, itemColor);
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
                break;
            case HORIZONTAL_CROSS:
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
            case HORIZONTAL:
                drawLeft(outRect, itemColor);
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
                break;
        }
    }

    /**
     * 获取矩阵的宽高
     * 通过反射 获取RecyclerView的Recycler变量
     * 调用Recycler的getRowCountForAccessibility、getColumnCountForAccessibility方法获取宽和高
     *
     * @param parent RecyclerView父布局
     * @param state  RecyclerView父布局的State
     */
    private void getRowsAndColumns(RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            try {
                // /通过类的字节码得到该类中声明的所有属性，无论私有或公有
                Field fieldPassword = parent.getClass().getDeclaredField("mRecycler");
                // 设置访问权限（这点对于有过android开发经验的可以说很熟悉）
                fieldPassword.setAccessible(true);
                // 得到私有的变量值
//                RecyclerView recycler = new RecyclerView(parent.getContext());
                RecyclerView.Recycler recy = (RecyclerView.Recycler) fieldPassword.get(parent);
                mRow = gridLayoutManager.getRowCountForAccessibility(recy, state);  //行数
                mColumn = gridLayoutManager.getColumnCountForAccessibility(recy, state); //列数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
