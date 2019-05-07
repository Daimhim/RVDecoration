package rv.daimhim.rvdecoration;

import android.arch.lifecycle.BuildConfig;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import rv.daimhim.rvdecoration.decoration.version100.CreateHelp;

public class DecorationBuilder {

    public static class Builder {
        private final DecorationBuilder.DecorationParams P;

        public Builder(RecyclerView pRecyclerView) {
            P = new DecorationParams();
            P.mContext = pRecyclerView.getContext();
            P.mRecyclerView = pRecyclerView;
        }
        public RecycleDecoration create() {
            RecycleDecoration lRecycleDecoration = new RecycleDecoration();
            if (BuildConfig.VERSION_CODE <= 100) {
                new CreateHelp().createDecorationVersion100(lRecycleDecoration, P);
            }
            P.mRecyclerView.addItemDecoration(lRecycleDecoration);
            return lRecycleDecoration;
        }

        public RecycleDecoration create(int versionCode) {
            RecycleDecoration lRecycleDecoration = new RecycleDecoration();
            switch (versionCode) {
                case 100:
                    new CreateHelp().createDecorationVersion100(lRecycleDecoration, P);
                    break;
                case 101:
                    new CreateHelp().createDecorationVersion101(lRecycleDecoration, P);
                    break;
                case 102:
                    break;
                default:
                    new CreateHelp().createDecorationVersion100(lRecycleDecoration, P);
                    break;
            }
            P.mRecyclerView.addItemDecoration(lRecycleDecoration);
            return lRecycleDecoration;
        }

        public Builder linearlayout(@ColorRes int color, @DimenRes int size) {
            P.orientation = ((LinearLayoutManager) P.mRecyclerView.getLayoutManager()).getOrientation();
            P.color = color;
            P.size = size;
            return this;
        }

        public Builder staggeredGridlayout(@ColorRes int color, @DimenRes int size) {
            P.orientation = ((StaggeredGridLayoutManager) P.mRecyclerView.getLayoutManager()).getOrientation();
            P.color = color;
            P.size = size;
            return this;
        }



        public Builder setBaseCount(int count) {
            P.baseCount = count;
            return this;
        }

        public Builder setFootCount(int count) {
            P.footCount = count;
            return this;
        }

        //间隔
        public Builder head(@DimenRes int size) {
            P.head = size;
            return this;
        }
        //间隔
        public Builder food(@DimenRes int size) {
            P.food = size;
            return this;
        }

        //间隔颜色
        public Builder divider(@ColorRes int color) {
            P.color = color;
            return this;
        }
        //间隔
        public Builder spacing(@DimenRes int size) {
            P.size = size;
            return this;
        }

        //方向
        public Builder orientation(int orientation) {
            P.orientation = orientation;
            return this;
        }

        //两侧
        public Builder bothSides(@DimenRes int size){
            P.bothSides = size;
            return this;
        }

        //测量
        public Builder setMeasureTarget(RecycleDecoration.MeasureTarget pMeasureTarget) {
            P.mMeasureTarget = pMeasureTarget;
            return this;
        }

        /**------------------------------一下是过时方法，不完全兼容---------------------------------------**/
        //垂直间隔颜色
        @Deprecated
        public Builder verticalDivider(@ColorRes int color) {
            P.color = P.verticalColor = color;
            return this;
        }
        @Deprecated
        public Builder applyHead(boolean isHead) {
            P.isHead = isHead;
            return this;
        }
        @Deprecated
        public Builder applyFoot(boolean isFood) {
            P.isFood = isFood;
            return this;
        }
        //垂直间隔
        @Deprecated
        public Builder verticalSpacing(@DimenRes int size) {
            P.size = P.verticalSize = size;
            return this;
        }


        //水平间隔
        @Deprecated
        public Builder horizontalSpacing(@DimenRes int size) {
            P.horizontalSize = size;
            return this;
        }
        //水平间隔颜色
        @Deprecated
        public Builder horizontalDivider(@ColorRes int color) {
            P.color = P.horizontalColor = color;
            return this;
        }
    }

    public static class DecorationParams {
        @Deprecated
        public int verticalColor = R.color.cl_999999;
        @Deprecated
        public int horizontalColor = R.color.cl_999999;
        @Deprecated
        public int verticalSize = R.dimen.dimen_size_1;
        @Deprecated
        public int horizontalSize = R.dimen.dimen_size_1;
        @Deprecated
        public boolean isHead;
        @Deprecated
        public boolean isFood;

        public int orientation = OrientationHelper.VERTICAL;

        public int head = R.dimen.dimen_size_0;
        public int food = R.dimen.dimen_size_0;

        public int size = R.dimen.dimen_size_1;
        public int color = R.color.cl_999999;
        public Context mContext;
        public RecyclerView mRecyclerView;
        public int baseCount = -1;
        public int footCount = -1;
        public RecycleDecoration.MeasureTarget mMeasureTarget;
        public int bothSides;

    }
}