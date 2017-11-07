package rv.daimhim.rvdecoration;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

/**
 * Created by Daimhim on 2017/2/27.
 */

public class DecorationBuilder {

    protected int mOrientation = RecycleDecoration.VERTICAL;  //方向

    protected int mDividerWidth = 2;//分割线高度，默认为2px

    protected int mDividerColor = android.R.color.white;

    protected RecycleDecoration.CustomizedOffsets mCustomizedOffsets;

    protected RecycleDecoration.CustomizedDraw mCustomizedDraw;

    @NonNull
    protected Context mContext;

    protected boolean leftLine;
    protected boolean topLine;
    protected boolean rightLine;
    protected boolean bottomLine;

    protected int leftRect;
    protected int topRect;
    protected int rightRect;
    protected int bottomRect;
    @ColorRes
    protected int leftRectColor;
    @ColorRes
    protected int topRectColor;
    @ColorRes
    protected int rightRectColor;
    @ColorRes
    protected int bottomRectColor;

    protected RecycleDecoration mRecycleDecoration;

    public DecorationBuilder(RecycleDecoration recycleDecoration) {
        this.mRecycleDecoration = recycleDecoration;
    }

    private static DecorationBuilder sMDecorationBuilder;

    public static DecorationBuilder Builder(RecycleDecoration recycleDecoration){
        if (null== sMDecorationBuilder){
            sMDecorationBuilder = new DecorationBuilder(recycleDecoration);
        }
        sMDecorationBuilder.mRecycleDecoration=recycleDecoration;
        return sMDecorationBuilder;
    }


    public DecorationBuilder setCustomizedOffsets(RecycleDecoration.CustomizedOffsets customizedOffsets) {
        mCustomizedOffsets = customizedOffsets;
        return this;
    }

    public DecorationBuilder setCustomizedDraw(RecycleDecoration.CustomizedDraw customizedDraw) {
        mCustomizedDraw = customizedDraw;
        return this;
    }

    public DecorationBuilder setLeftLine(boolean leftLine) {
        this.leftLine = leftLine;
        return this;
    }

    public DecorationBuilder setTopLine(boolean topLine) {
        this.topLine = topLine;
        return this;
    }

    public DecorationBuilder setRightLine(boolean rightLine) {
        this.rightLine = rightLine;
        return this;
    }

    public DecorationBuilder setBottomLine(boolean bottomLine) {
        this.bottomLine = bottomLine;
        return this;
    }

    public DecorationBuilder setLeftRect(int leftRect) {
        this.leftRect = leftRect;
        return this;
    }

    public DecorationBuilder setTopRect(int topRect) {
        this.topRect = topRect;
        return this;
    }

    public DecorationBuilder setRightRect(int rightRect) {
        this.rightRect = rightRect;
        return this;
    }

    public DecorationBuilder setBottomRect(int bottomRect) {
        this.bottomRect = bottomRect;
        return this;
    }

    public DecorationBuilder setLeftRectColor(int leftRectColor) {
        this.leftRectColor = leftRectColor;
        return this;
    }

    public DecorationBuilder setRightRectColor(int rightRectColor) {
        this.rightRectColor = rightRectColor;
        return this;
    }

    public DecorationBuilder setTopRectColor(int topRectColor) {
        this.topRectColor = topRectColor;
        return this;
    }

    public DecorationBuilder setBottomRectColor(int bottomRectColor) {
        this.bottomRectColor = bottomRectColor;
        return this;
    }

    public DecorationBuilder setRecycleDecoration(RecycleDecoration recycleDecoration){
        this.mRecycleDecoration = recycleDecoration;
        return this;
    }

    public DecorationBuilder setOrientation(int orientation) {
        this.mOrientation = orientation;
        return this;
    }

    public DecorationBuilder setDividerWidth(int dividerWidth) {
        this.mDividerWidth = dividerWidth;
        return this;
    }

    public DecorationBuilder setDividerColor(@ColorRes int dividerColor) {
        this.mDividerColor = dividerColor;
        return this;
    }

    @Deprecated
    public DecorationBuilder setContext(Context context) {
        this.mContext = context;
        return this;
    }

    public RecycleDecoration builder(){
        RecycleDecoration recycleDecoration = this.mRecycleDecoration.create(this);
        this.mOrientation = RecycleDecoration.VERTICAL;  //方向
        this.mDividerWidth = 2;//分割线高度，默认为2px
        this.mDividerColor = android.R.color.white;
        this.mContext = null;
        this.leftLine = false;
        this.topLine = false;
        this.rightLine = false;
        this.bottomLine = false;
        this.leftRect = 0;
        this.topRect = 0;
        this.rightRect = 0;
        this.bottomRect = 0;
        this.leftRectColor  = 0;
        this.topRectColor = 0;
        this.rightRectColor = 0;
        this.bottomRectColor = 0;
        this.mCustomizedOffsets = null;
        this.mCustomizedDraw = null;
        this.mRecycleDecoration = null;
        return recycleDecoration;
    }

}
