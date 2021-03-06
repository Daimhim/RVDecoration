package rv.daimhim.rvdecoration.decoration.version103;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.RecycleDecoration;
import rv.daimhim.rvdecoration.decoration.core.AbsGridDecoration;
import rv.daimhim.rvdecoration.decoration.core.AbsLinearDecoration;

/**
 * @Classname CreateV103Help
 * @Description TODO
 * @Date 2019/7/4 17:41
 * @Created by Daimhim
 * Class description Daimhim太懒了什么都没有留下
 */
public class CreateV103Help {

    public void createDecorationVersion(RecycleDecoration pLRecycleDecoration,
                                        DecorationBuilder.DecorationParams p){
        RecyclerView.LayoutManager lLayoutManager = p.mRecyclerView.getLayoutManager();
        if (lLayoutManager instanceof GridLayoutManager){
//            GridVerticalDecoration103
            AbsGridDecoration lGridDecoration = null;
            if (p.orientation == GridLayoutManager.VERTICAL){
                lGridDecoration = new GridVerticalDecoration103(p);
            }else {

            }
            pLRecycleDecoration.setDrawBeforeTarget(lGridDecoration);
            pLRecycleDecoration.setMeasureTarget(lGridDecoration);
        }else if (lLayoutManager instanceof LinearLayoutManager){
            AbsLinearDecoration lLinearDecoration = null;
            if (p.orientation == OrientationHelper.VERTICAL) {
                lLinearDecoration = new LinearVerticalDecoration103(p);
            }else {

            }
            pLRecycleDecoration.setMeasureTarget(lLinearDecoration);
            pLRecycleDecoration.setDrawBeforeTarget(lLinearDecoration);
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
