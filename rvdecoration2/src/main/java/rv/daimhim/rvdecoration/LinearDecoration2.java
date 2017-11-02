package rv.daimhim.rvdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 使用方法 请看DecorationBuilder类
 * 该类用于LinearLayoutManager
 * Created by Daimhim on 2017/2/16.
 */

public class LinearDecoration2 implements RecycleDecoration2.DrawBeforeTarget ,RecycleDecoration2.DrawAfterTarget,RecycleDecoration2.MeasureTarget {


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int adapterPosition = parent.getChildAdapterPosition(view);
    }
}
