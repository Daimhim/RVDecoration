package rv.daimhim.rvdecoration.decoration.version102;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.RecycleDecoration;
import rv.daimhim.rvdecoration.decoration.version100.GridDecoration;
import rv.daimhim.rvdecoration.decoration.version100.LinearDecoration;
import rv.daimhim.rvdecoration.decoration.version100.StaggeredGridDecoration;

/**
 * @Classname CreateV102Help
 * @Description TODO
 * @Date 2019/5/8 0:08
 * @Created by Daimhim
 * Class description Daimhim太懒了什么都没有留下
 */
public class CreateV102Help {
    public void createDecorationVersion102(RecycleDecoration pLRecycleDecoration,
                                           DecorationBuilder.DecorationParams p){
        RecyclerView.LayoutManager lLayoutManager = p.mRecyclerView.getLayoutManager();
        if (lLayoutManager instanceof GridLayoutManager){
//            GridDecoration lGridDecoration = null;
//            lGridDecoration = new GridDecoration(p);
//            pLRecycleDecoration.setDrawBeforeTarget(lGridDecoration);
//            pLRecycleDecoration.setMeasureTarget(lGridDecoration);
        }else if (lLayoutManager instanceof LinearLayoutManager){
//            LinearDecoration lLinearDecoration = null;
//            if (p.orientation == OrientationHelper.VERTICAL) {
//                lLinearDecoration = new LinearDecoration(p.mContext,
//                        p.verticalColor, p.verticalSize,p.orientation);
//            }else {
//                lLinearDecoration = new LinearDecoration(p.mContext,
//                        p.horizontalColor, p.horizontalSize,p.orientation);
//            }
//            pLRecycleDecoration.setMeasureTarget(lLinearDecoration);
//            pLRecycleDecoration.setDrawBeforeTarget(lLinearDecoration);
        }else if (lLayoutManager instanceof StaggeredGridLayoutManager){
//            StaggeredGridDecoration lStaggeredGridDecoration = null;
//            int lSpanCount = ((StaggeredGridLayoutManager) lLayoutManager).getSpanCount();
//            if (p.orientation == OrientationHelper.VERTICAL) {
//                lStaggeredGridDecoration =  new StaggeredGridDecoration(p.mContext,
//                        p.verticalColor, p.verticalSize,p.orientation,lSpanCount);
//            }else {
//                lStaggeredGridDecoration = new StaggeredGridDecoration(p.mContext,
//                        p.horizontalColor, p.horizontalSize,p.orientation,lSpanCount);
//            }
//            pLRecycleDecoration.setDrawBeforeTarget(lStaggeredGridDecoration);
//            pLRecycleDecoration.setMeasureTarget(lStaggeredGridDecoration.getMeasureTarget());
        }
        if (p.mMeasureTarget !=null){
            pLRecycleDecoration.setMeasureTarget(p.mMeasureTarget);
        }
    }
}
