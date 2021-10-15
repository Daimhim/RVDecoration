package org.daimhim.rvadapter

import android.util.Pair
import android.util.SparseIntArray

class ExpandableHelper(private val expandable: RecyclerContract.Expandable) {
    /**
     * Key groupPosition
     * Value group>Position
     */
    private var mSparseArray: SparseIntArray? = null

    /**
     * 在数据改变之后调用
     */
    fun notifyPositionChanged() {
        if (null == mSparseArray) {
            mSparseArray = SparseIntArray()
        } else {
            mSparseArray?.clear()
        }
        var num = 0
        for (i in 0 until expandable.groupCount) {
            mSparseArray?.put(i, num)
            num += expandable.getChildrenCount(i)
            num++
        }
        mSparseArray?.put(expandable.groupCount, num)
    }

    /**
     * < 0 一维
     * => 0 二维
     */
    fun indexOfValue(position: Int):Int{
        return mSparseArray!!.indexOfValue(position)
    }

    /**
     * 在数据改变之后调用
     * @param groupPosition Group位置
     */
    fun notifyGroupPositionChanged(groupPosition: Int) {
        var num = 0
        for (i in 0 until groupPosition) {
            num += expandable.getChildrenCount(i)
            num++
        }
        mSparseArray!!.put(expandable.groupCount, num)
    }

    /**
     * 逆序 通过一维向量 查找二维数组
     * @param position 一维位置
     * @return 二维对象
     */
    fun indexOfPosition(position: Int): Pair<Int, Int> {
        var num = 0
        for (i in 0 until expandable.groupCount) {
            num += expandable.getChildrenCount(i)
            num++
            if (num > position) {
                num -= expandable.getChildrenCount(i)
                num--
                if (num == position) {
                    return Pair(i, -1)
                }
                for (j in 0 until expandable.getChildrenCount(i)) {
                    num++
                    if (num == position) {
                        return Pair(i, j)
                    }
                }
            }
        }
        return Pair(-1, -1)
    }

    /**
     * 通过起始点查找 二维数组的起始位置
     * @param groupPosition  起始点
     * @return 二维数组对象
     */
    fun indexOfGroupPosition(groupPosition: Int): Pair<Int, Int> {
        var num = 0
        for (i in 0 until groupPosition) {
            num += expandable.getChildrenCount(i)
            num++
        }
        return Pair(groupPosition, num)
    }

    /**
     * 顺序 通过二维数组 查找一维向量
     * @param groupPosition 二维起始点
     * @param position 位置
     * @return 一维位置
     */
    fun indexOfItemInPosition(groupPosition: Int, position: Int): Int {
        var num = 0
        for (i in 0 until groupPosition) {
            num += expandable.getChildrenCount(i)
            num++
        }
        return num + position
    }


}