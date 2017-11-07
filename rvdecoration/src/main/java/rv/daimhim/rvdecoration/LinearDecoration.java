package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 使用方法 请看DecorationBuilder类
 * 该类用于LinearLayoutManager
 * Created by Daimhim on 2017/2/16.
 */

public class LinearDecoration extends RecycleDecoration {

    @Deprecated
    public LinearDecoration() {
    }

    public LinearDecoration(Context context) {
        super(context);
    }

    @Override
    protected void getDefaultOffsets(Rect outRect, Rect itemColor, View view, RecyclerView parent,
                                     RecyclerView.State state) {
        int count = state.getItemCount() - 1; //总长度
        int position = parent.getChildAdapterPosition(view); //当前位置

        if (position != 0 && position != count) {
            arrangementContent(outRect, itemColor, getOrientation());
        } else if (position == 0) {
            arrangementFirst(outRect, itemColor, getOrientation());
        } else if (position == count) {
            arrangementEnd(outRect, itemColor, getOrientation());
        }
    }

    public void arrangementEnd(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL:
                drawLeft(outRect, itemColor);
                drawBottom(outRect, itemColor);
                drawRight(outRect, itemColor);
                break;
            case HORIZONTAL:
                drawTop(outRect, itemColor);
                drawRight(outRect, itemColor);
                drawBottom(outRect, itemColor);
                break;
            default:
                break;
        }
    }

    public void arrangementFirst(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL:
                drawTop(outRect, itemColor);
                drawLeft(outRect, itemColor);
                drawRight(outRect, itemColor);
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
                break;
            case HORIZONTAL:
                drawLeft(outRect, itemColor);
                drawTop(outRect, itemColor);
                drawBottom(outRect, itemColor);
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
                break;
            default:
                break;
        }
    }

    public void arrangementContent(Rect outRect, Rect itemColor, int orientation) {
        switch (orientation) {
            case VERTICAL:
                drawLeft(outRect, itemColor);
                drawRight(outRect, itemColor);
                outRect.bottom = getDividerWidth();
                itemColor.bottom = getDividerColor();
                break;
            case HORIZONTAL:
                drawTop(outRect, itemColor);
                drawBottom(outRect, itemColor);
                outRect.right = getDividerWidth();
                itemColor.right = getDividerColor();
                break;
            default:
                break;
        }
    }

}
