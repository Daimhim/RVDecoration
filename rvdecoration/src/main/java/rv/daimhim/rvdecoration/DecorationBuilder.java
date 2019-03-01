package rv.daimhim.rvdecoration;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class DecorationBuilder {

    public static class Builder{
        private final DecorationBuilder.DecorationParams P;

        public Builder(RecyclerView pRecyclerView) {
            P = new DecorationParams();
            P.mContext = pRecyclerView.getContext();
            P.mRecyclerView = pRecyclerView;
        }
        public Builder linearlayout(@ColorRes int color, @DimenRes int size){
            P.orientation = ((LinearLayoutManager)P.mRecyclerView.getLayoutManager()).getOrientation();
            P.horizontalColor = P.verticalColor = color;
            P.horizontalSize = P.verticalSize = size;
            return this;
        }
        public Builder staggeredGridlayout(@ColorRes int color, @DimenRes int size){
            P.orientation = ((StaggeredGridLayoutManager)P.mRecyclerView.getLayoutManager()).getOrientation();
            P.horizontalColor = P.verticalColor = color;
            P.horizontalSize = P.verticalSize = size;
            return this;
        }
        //垂直间隔颜色
        public Builder verticalDivider(@ColorRes int color) {
            P.verticalColor = color;
            return this;
        }

        //水平间隔颜色
        public Builder horizontalDivider(@ColorRes int color) {
            P.horizontalColor = color;
            return this;
        }

        //垂直间隔
        public Builder verticalSpacing(@DimenRes int size) {
            P.verticalSize = size;
            return this;
        }

        //水平间隔
        public Builder horizontalSpacing(@DimenRes int size) {
            P.horizontalSize = size;
            return this;
        }
        //方向
        public Builder orientation(int orientation){
            P.orientation = orientation;
            return this;
        }
        //测量
        public Builder setMeasureTarget(RecycleDecoration.MeasureTarget pMeasureTarget) {
            P.mMeasureTarget = pMeasureTarget;
            return this;
        }

        public RecycleDecoration create(){
            RecycleDecoration lRecycleDecoration = new RecycleDecoration();
            switch (BuildConfig.VERSION_CODE){
                case 100:
                    new CreateHelp().createDecorationVersion100(lRecycleDecoration,P);
                    break;
                default:
                    break;
            }
            P.mRecyclerView.addItemDecoration(lRecycleDecoration);
            return lRecycleDecoration;
        }
    }

    static class DecorationParams{
        Context mContext;
        RecyclerView mRecyclerView;
        @ColorRes
        int verticalColor = R.color.cl_999999;
        @ColorRes
        int horizontalColor = R.color.cl_999999;
        @DimenRes
        int verticalSize = R.dimen.dimen_size_1;
        @DimenRes
        int horizontalSize = R.dimen.dimen_size_1;

        RecycleDecoration.MeasureTarget mMeasureTarget;

        int orientation = OrientationHelper.VERTICAL;
    }
}