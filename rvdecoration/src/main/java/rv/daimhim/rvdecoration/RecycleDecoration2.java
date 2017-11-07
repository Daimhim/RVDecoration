package rv.daimhim.rvdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 项目名称：com.example.used.decoration
 * 项目版本：usedlibrary
 * 创建人：Daimhim
 * 创建时间：2017/10/23 15:49
 * 修改人：Daimhim
 * 修改时间：2017/10/23 15:49
 * 类描述：
 * 修改备注：
 * @author Daimhim
 */

public class RecycleDecoration2 extends RecyclerView.ItemDecoration{
    protected DrawBeforeTarget mDrawBeforeTarget;
    protected DrawAfterTarget mDrawAfterTarget;
    protected MeasureTarget mMeasureTarget;
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (null!=mDrawBeforeTarget){
            mDrawBeforeTarget.onDraw(c, parent, state);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (null!=mDrawAfterTarget){
            mDrawAfterTarget.onDrawOver(c, parent, state);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (null!=mMeasureTarget){
            mMeasureTarget.getItemOffsets(outRect, view, parent, state);
        }
    }

    public void setDrawBeforeTarget(DrawBeforeTarget drawBeforeTarget) {
        mDrawBeforeTarget = drawBeforeTarget;
    }

    public void setDrawAfterTarget(DrawAfterTarget drawAfterTarget) {
        mDrawAfterTarget = drawAfterTarget;
    }

    public void setMeasureTarget(MeasureTarget measureTarget) {
        mMeasureTarget = measureTarget;
    }

    interface DrawBeforeTarget{
        /**
         * 目标绘制之前
         * @param c 画板
         * @param parent  Recycler
         * @param state   状态
         */
        void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state);
    }
    interface DrawAfterTarget{
        /**
         * 目标绘制之后
         * @param c 画板
         * @param parent  Recycler
         * @param state   状态
         */
        void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state);
    }
    interface MeasureTarget{
        /**
         * 测量
         * @param outRect 结果
         * @param view   目标item
         * @param parent RecyclerView
         * @param state  状态  可保存数据
         */
        void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
    }
}
