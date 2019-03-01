package org.daimhim.irregularlayout.roundedimageview;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 项目名称：3.4.4
 * 类描述：
 * 创建人：meyki-bear
 * 创建时间：2016/12/1 12:02
 * 修改人：meyki-bear
 * 修改时间：2016/12/1 12:02
 * 修改备注：
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        Corner.TOP_LEFT, Corner.TOP_RIGHT,
        Corner.BOTTOM_LEFT, Corner.BOTTOM_RIGHT
})
public @interface Corner {
    int TOP_LEFT = 0;
    int TOP_RIGHT = 1;
    int BOTTOM_RIGHT = 2;
    int BOTTOM_LEFT = 3;
}
