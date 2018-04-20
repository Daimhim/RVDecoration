package rv.daimhim.rvdecorationtest;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 项目名称：com.meyki.sclient.widget
 * 项目版本：alliance
 * 创建时间：2018.04.20 14:51
 * 修改人：Daimhim
 * 修改时间：2018.04.20 14:51
 * 类描述：
 * 修改备注：
 *
 * @author：Daimhim
 */

public class IndicatorLayout extends LinearLayout implements ViewPager.OnPageChangeListener {
    @DrawableRes
    private int mSelected = android.R.drawable.presence_online;
    @DrawableRes
    private int mUnSelected = android.R.drawable.presence_offline;

    private int mCurrentSelected = -1;
    private int mCurrentPosition = -1;
    private Context mContext;

    private int mTotal = 0;
    @DimenRes
    private int mSpacing = 5;

    private WeakHandler mWeakHandler;
    private boolean isAutoPlay;
    private int delayTime = 3000;
    public int mBufferPage = 1;
    private int mActualCount = 0;

    private ViewPager mViewPager = null;

    public IndicatorLayout(Context context) {
        this(context, null);
    }

    public IndicatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    /**
     * 设置选中
     * @param selected Drawable资源
     */
    public void setSelected(int selected) {
        mSelected = selected;
    }

    /**
     * 设置未选中
     * @param unSelected  Drawable资源
     */
    public void setUnSelected(int unSelected) {
        mUnSelected = unSelected;
    }

    /**
     * 绑定ViewPager
     * @param viewPager  视图对象
     * @param isLinkage  是否启动监听
     */
    public void bindViewPager(ViewPager viewPager, boolean isLinkage) {
        mViewPager = viewPager;
        if (isLinkage) {
            viewPager.addOnPageChangeListener(this);
        }
    }

    /**
     * 可以直接使用资源文件
     * @param spacing 间距
     */
    public void setSpacing(int spacing) {
        mSpacing = spacing;
    }

    /**
     * 是否启动轮播，如果不设置 则不会轮播
     * @param isAutoPlay 是否
     */
    public void isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
    }

    /**
     * 轮播间隔时间
     * @param delayTime 毫秒
     */
    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    /**
     * 刷新长度
     * @param total 实际长度
     */
    public void onRefresh(int total) {
        mTotal = total;
        mActualCount = total + mBufferPage * 2;
        if (getChildCount() > 0) {
            removeAllViews();
        }
        for (int i = 0; i < mTotal; i++) {
            addView(createPointer(mContext, i));
        }
        movePointer(0);
    }

    /**
     * 启动自动轮播
     */
    public void startAutoPlay() {
        if (null == mWeakHandler) {
            mWeakHandler = new WeakHandler();
        }
        mWeakHandler.removeCallbacks(task);
        mWeakHandler.postDelayed(task, delayTime);
    }

    /**
     * 轮播一般都会有缓冲
     * @param bufferPage 最小不能为0
     */
    public void setBufferPage(int bufferPage) {
        if (mBufferPage > 0) {
            mBufferPage = bufferPage;
        }
    }

    /**
     * 增加体验 非必须
     * 停止轮播
     */
    public void stopAutoPlay() {
        if (null != mWeakHandler) {
            mWeakHandler.removeCallbacks(task);
            mWeakHandler.removeCallbacksAndMessages(null);
        }
    }


    private final Runnable task = new Runnable() {
        @Override
        public void run() {
//        mBufferPage = 2
//        mActualCount = 13
//        mTotal = 9
//        position (0 = 10) (1 = 9) 2 3 4 5 6 7 8 9 10 (11 = 2) (12 = 3)
            if (mTotal > 1 && isAutoPlay) {
                //越界
                if (mCurrentPosition + 1 >= mActualCount - mBufferPage) {
                    mViewPager.setCurrentItem(mBufferPage, false);
                } else {
                    mViewPager.setCurrentItem(mCurrentPosition + 1, true);
                }
                mViewPager.postDelayed(task, delayTime);
            }
        }
    };

    /**
     * 在不开启监听的情况下可以直接移动指示器，不会影响ViewPager
     * @param position 移动位置
     */
    public void movePointer(int position) {
        if (getChildCount() > position) {
            ImageView childAt = null;
            //取消上一次选中
            if (mCurrentSelected >= 0) {
                childAt = (ImageView) getChildAt(mCurrentSelected);
                childAt.setImageResource(mUnSelected);
            }
            //选中当前
            childAt = (ImageView) getChildAt(position);
            childAt.setImageResource(mSelected);
            mCurrentSelected = position;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        if (position > mBufferPage - 1 && position < mActualCount - mBufferPage) {
            movePointer(position - mBufferPage);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        int position = mViewPager.getCurrentItem();
//        mBufferPage = 2
//        mActualCount = 13
//        mTotal = 9
//        position (0 = 10) (1 = 9) 2 3 4 5 6 7 8 9 10 (11 = 2) (12 = 3)
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                //No operation
                //当前位置在最小缓冲页- 跳转到最大
                if (position < mBufferPage) {
                    mViewPager.setCurrentItem(mActualCount - mBufferPage - 1, false);
                } else if (position > mActualCount - mBufferPage - 1) {
                    mViewPager.setCurrentItem(mBufferPage, false);
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                //start Sliding
                if (position < mBufferPage) {
                    mViewPager.setCurrentItem(mActualCount - mBufferPage - 1, false);
                } else if (position > mActualCount - mBufferPage - 1) {
                    mViewPager.setCurrentItem(mBufferPage, false);
                }
                break;
            case 2:
                //end Sliding
                break;
            default:
                break;
        }
    }

    private ImageView createPointer(Context context, int position) {
        ImageView imageView = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (getOrientation() == VERTICAL) {
            if (position != mTotal - 1) {
                layoutParams.setMargins(0, 0, 0, (int) context.getResources().getDimension(mSpacing));
            }
        } else {
            if (position != mTotal - 1) {
                layoutParams.setMargins(0, 0, (int) context.getResources().getDimension(mSpacing), 0);
            }
        }
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(mUnSelected);
        return imageView;
    }

}
