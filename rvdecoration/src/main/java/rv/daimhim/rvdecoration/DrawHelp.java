package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class DrawHelp {

    /**
     * 绘制类 根据缓存对象mViewHashMap中的数据 去绘制
     * @param c 画布
     * @param pRect 相对于Item四边的边距
     * @param pPaint 画笔
     * @param pView 当前Item的View对象
     */
    protected static void drawLine(Canvas c, Rect pRect, Paint pPaint,View pView) {
        int startX;
        int startY;
        int stopX;
        int stopY;
        if (pRect.left != 0) {
            startX = pView.getLeft() - (pRect.left / 2);
            startY = pView.getTop() - pRect.top;
            stopX = pView.getLeft() - (pRect.left / 2);
            stopY = pView.getBottom() + pRect.bottom;
            c.drawLine(startX, startY, stopX, stopY, pPaint);
        }
        if (pRect.top != 0) {
            startX = pView.getLeft() - pRect.left;
            startY = pView.getTop() - (pRect.top / 2);
            stopX = pView.getRight() + pRect.right;
            stopY = pView.getTop() - (pRect.top / 2);
            c.drawLine(startX, startY, stopX, stopY, pPaint);
        }
        if (pRect.right != 0) {
            startX = pView.getRight() + (pRect.right / 2);
            startY = pView.getTop() - pRect.top;
            stopX = pView.getRight() + (pRect.right / 2);
            stopY = pView.getBottom() + pRect.bottom;
            c.drawLine(startX, startY, stopX, stopY, pPaint);
        }
        if (pRect.bottom != 0) {
            startX = pView.getLeft() - pRect.left;
            startY = pView.getBottom() + (pRect.bottom / 2);
            stopX = pView.getRight() + pRect.right;
            stopY = pView.getBottom() + (pRect.bottom / 2);
            c.drawLine(startX, startY, stopX, stopY, pPaint);
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
    private void drawLine(Context pContext,Canvas c, Rect outRect, Rect outRectColor, Paint pPaint, View childAt) {
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
                pPaint.setColor(ContextCompat.getColor(pContext, outRectColor.left));
            }
            pPaint.setStrokeWidth(outRect.left);
            c.drawLine(startX, startY, stopX, stopY, pPaint);
        }
        if (outRect.right != 0) {
            startX = childAt.getRight() + (outRect.right / 2);
            startY = childAt.getTop() - outRect.top;
            stopX = childAt.getRight() + (outRect.right / 2);
            stopY = childAt.getBottom() + outRect.bottom;
            if (outRectColor.right != 0) {
                pPaint.setColor(ContextCompat.getColor(pContext, outRectColor.right));
            }
            pPaint.setStrokeWidth(outRect.right);
            c.drawLine(startX, startY, stopX, stopY, pPaint);
        }
        if (outRect.top != 0) {
            startX = childAt.getLeft() - outRect.left;
            startY = childAt.getTop() - (outRect.top / 2);
            stopX = childAt.getRight() + outRect.right;
            stopY = childAt.getTop() - (outRect.top / 2);
            if (outRectColor.top != 0) {
                pPaint.setColor(ContextCompat.getColor(pContext, outRectColor.top));
            }
            pPaint.setStrokeWidth(outRect.top);
            c.drawLine(startX, startY, stopX, stopY, pPaint);
        }
        if (outRect.bottom != 0) {
            startX = childAt.getLeft() - outRect.left;
            startY = childAt.getBottom() + (outRect.bottom / 2);
            stopX = childAt.getRight() + outRect.right;
            stopY = childAt.getBottom() + (outRect.bottom / 2);
            if (outRectColor.bottom != 0) {
                pPaint.setColor(ContextCompat.getColor(pContext, outRectColor.bottom));
            }
            pPaint.setStrokeWidth(outRect.bottom);
            c.drawLine(startX, startY, stopX, stopY, pPaint);
        }
    }

}
