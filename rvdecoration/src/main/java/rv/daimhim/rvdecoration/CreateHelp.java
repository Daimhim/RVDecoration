package rv.daimhim.rvdecoration;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class CreateHelp {
    protected void createDecorationVersion100(RecycleDecoration pLRecycleDecoration,
                                              DecorationBuilder.DecorationParams p){
        RecyclerView.LayoutManager lLayoutManager = p.mRecyclerView.getLayoutManager();
        if (lLayoutManager instanceof GridLayoutManager){

        }else if (lLayoutManager instanceof LinearLayoutManager){
            LinearDecoration lLinearDecoration = null;
            if (p.orientation == OrientationHelper.VERTICAL) {
                lLinearDecoration = new LinearDecoration(p.mContext,
                        p.verticalColor, p.verticalSize,p.orientation);
            }else {
                lLinearDecoration = new LinearDecoration(p.mContext,
                        p.horizontalColor, p.horizontalSize,p.orientation);
            }
            pLRecycleDecoration.setMeasureTarget(lLinearDecoration);
            pLRecycleDecoration.setDrawBeforeTarget(lLinearDecoration);
        }else if (lLayoutManager instanceof StaggeredGridLayoutManager){
            StaggeredGridDecoration lStaggeredGridDecoration = null;
            int lSpanCount = ((StaggeredGridLayoutManager) lLayoutManager).getSpanCount();
            if (p.orientation == OrientationHelper.VERTICAL) {
                lStaggeredGridDecoration =  new StaggeredGridDecoration(p.mContext,
                        p.verticalColor, p.verticalSize,p.orientation,lSpanCount);
            }else {
                lStaggeredGridDecoration = new StaggeredGridDecoration(p.mContext,
                        p.horizontalColor, p.horizontalSize,p.orientation,lSpanCount);
            }
            pLRecycleDecoration.setDrawBeforeTarget(lStaggeredGridDecoration);
            pLRecycleDecoration.setMeasureTarget(lStaggeredGridDecoration.getMeasureTarget());
        }
    }
}
