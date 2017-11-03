package rv.daimhim.rvdecoration.layoutmanager;

import android.support.v4.util.SparseArrayCompat;

/**
 * 项目名称：rv.daimhim.rvdecoration.layoutmanager
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/11/3 9:21
 * 修改人：Daimhim
 * 修改时间：2017/11/3 9:21
 * 类描述：
 * 修改备注：
 */

public class Pool<T> {
    private SparseArrayCompat<T> mPool;
    private New<T> mNewInstance;

    public Pool(New<T> newInstance) {
        mPool = new SparseArrayCompat<>();
        mNewInstance = newInstance;
    }

    public T get(int key) {
        T res = mPool.get(key);
        if (res == null) {
            res = mNewInstance.get();
            mPool.put(key, res);
        }
        return res;
    }

    public interface New<T> {
        T get();
    }
}
