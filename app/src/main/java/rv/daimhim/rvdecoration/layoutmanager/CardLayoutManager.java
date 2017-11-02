package rv.daimhim.rvdecoration.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 项目名称：rv.daimhim.rvdecoration.layoutmanager
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/11/2 23:37
 * 修改人：Daimhim
 * 修改时间：2017/11/2 23:37
 * 类描述：
 * 修改备注：
 * @author Daimhim
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {
    public static final int DEFAULT_GROUP_SIZE = 5;
    private int mGroupSize;
    private int mHorizontalOffset;
    private int mVerticalOffset;
    private int mTotalWidth;
    private int mTotalHeight;
    private int mGravityOffset;
    private boolean isGravityCenter;
    public CardLayoutManager(boolean center) {
        this(DEFAULT_GROUP_SIZE, center);
    }

    public CardLayoutManager(int groupSize, boolean center) {
        mGroupSize = groupSize;
        isGravityCenter = center;
//        mItemFrames = new Pool<>(new Pool.New<Rect>() {
//            @Override
//            public Rect get() { return new Rect();}
//        });
    }
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
