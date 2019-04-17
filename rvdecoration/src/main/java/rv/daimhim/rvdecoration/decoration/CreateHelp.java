package rv.daimhim.rvdecoration.decoration;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import rv.daimhim.rvdecoration.DecorationBuilder;
import rv.daimhim.rvdecoration.RecycleDecoration;
import rv.daimhim.rvdecoration.decoration.GridDecoration;
import rv.daimhim.rvdecoration.decoration.LinearDecoration;
import rv.daimhim.rvdecoration.decoration.StaggeredGridDecoration;
import rv.daimhim.rvdecoration.decoration.base.AbsGridDecoration;
import rv.daimhim.rvdecoration.decoration.base.AbsLinearDecoration;
import rv.daimhim.rvdecoration.decoration.LinearHorizontalDecoration;
import rv.daimhim.rvdecoration.decoration.LinearVerticalDecoration;

public class CreateHelp {
    public void createDecorationVersion100(RecycleDecoration pLRecycleDecoration,
                                              DecorationBuilder.DecorationParams p){
        RecyclerView.LayoutManager lLayoutManager = p.mRecyclerView.getLayoutManager();
        if (lLayoutManager instanceof GridLayoutManager){
            GridDecoration lGridDecoration = null;
            lGridDecoration = new GridDecoration(p);
            pLRecycleDecoration.setDrawBeforeTarget(lGridDecoration);
            pLRecycleDecoration.setMeasureTarget(lGridDecoration);
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
        if (p.mMeasureTarget !=null){
            pLRecycleDecoration.setMeasureTarget(p.mMeasureTarget);
        }
    }
    public void createDecorationVersion101(RecycleDecoration pLRecycleDecoration,
                                              DecorationBuilder.DecorationParams p){
        RecyclerView.LayoutManager lLayoutManager = p.mRecyclerView.getLayoutManager();
        if (lLayoutManager instanceof GridLayoutManager){
            AbsGridDecoration lAbsGridDecoration = null;
            if (p.orientation == GridLayoutManager.VERTICAL){
                lAbsGridDecoration = new GridVerticalDecoration();
            }
            pLRecycleDecoration.setDrawBeforeTarget(lAbsGridDecoration);
            pLRecycleDecoration.setMeasureTarget(lAbsGridDecoration);
        }else if (lLayoutManager instanceof LinearLayoutManager){
            AbsLinearDecoration lAbsLinearDecoration = null;
            if (p.orientation == OrientationHelper.VERTICAL) {
                lAbsLinearDecoration = new LinearVerticalDecoration(p);
            }else {
                lAbsLinearDecoration = new LinearHorizontalDecoration(p);
            }
            pLRecycleDecoration.setMeasureTarget(lAbsLinearDecoration);
            pLRecycleDecoration.setDrawBeforeTarget(lAbsLinearDecoration);
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
        if (p.mMeasureTarget !=null){
            pLRecycleDecoration.setMeasureTarget(p.mMeasureTarget);
        }
    }
}
