package rv.daimhim.rvdecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
}
