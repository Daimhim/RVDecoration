package org.daimhim.rvadapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：RVDecoration
 * 创建人：Daimhim
 * 创建时间：2017/12/25 15:20
 * 修改人：Daimhim
 * 修改时间：2017/12/25 15:20
 * 类描述：
 * 修改备注：
 * @author Daimhim
 */

public interface RecyclerContract {
    /**
     * 提供给外部的 接口  传入此接口 监听点击事件
     */
    interface OnItemClickListener {
        /**
         * 点击事件
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);
    }

    interface OnItemLongClickListener {
        /**
         * 长点击事件
         * @param view
         * @param position
         */
        void onItemLongClick(View view, int position);
    }

    /**
     * 空界面
     * @param <VH>
     */
    interface EmptyContract<VH extends RecyclerViewClick.ClickViewHolder> {
        /**
         * 是否空页面
         *
         * @return
         */
        boolean isEmptyView();

        /**
         * 设置空页面
         *
         * @param holder
         * @param position
         */
        void onBindEmptyViewHolder(RecyclerViewClick.ClickViewHolder holder, int position);

        /**
         * 设置空页面
         *
         * @param parent
         * @param viewType
         * @return
         */
        VH onCreateEmptyViewHolder(ViewGroup parent, int viewType);
    }


    interface OnGroupItemClickListener {
        /**
         *
         * @param view
         * @param groupPosition
         */
        void onGroupItemClick(View view, int groupPosition);
    }

    interface OnGroupItemLongClickListener {
        /**
         *
         * @param view
         * @param groupPosition
         */
        void onGroupItemLongClick(View view, int groupPosition);
    }

    interface OnChildItemClickListener {
        /**
         *
         * @param view
         * @param groupPosition
         * @param childPosition
         */
        void onChildItemClick(View view, int groupPosition, int childPosition);
    }

    interface OnChildItemLongClickListener {
        /**
         *
         * @param view
         * @param groupPosition
         * @param childPosition
         */
        void onChildItemLongClick(View view, int groupPosition, int childPosition);
    }

    /**
     * Adapter规范
     * @param <Ts>
     * @param <T>
     */
    interface SpecificationContract<Ts,T> {
        /**
         * 刷新
         * @param ts 数据类型
         */
        void onRefresh(Ts ts);

        /**
         * 添加多条
         * @param ts 数据类型
         * @param position
         */
        void onLoad(Ts ts, int position);

        /**
         * 插入
         * @param t  数据类型
         * @param position 位置
         *                 该方法可以和add合并
         */
        void insertItem(T t, int position);

        /**
         * 删除
         * @param position 位置
         */
        void deleteItem(int position);

        /**
         * 替换
         * @param t 数据类型
         * @param position
         */
        void replaceItem(T t, int position);

        /**
         * 获取数据
         * @param position  位置
         * @return 数据类型
         */
        T getItem(int position);
    }

    /**
     * Adapter规范
     * @param <Ts>
     * @param <T>
     */
    interface ExpandableContract<Ts,T> {
        /**
         * 刷新
         * @param ts 数据类型
         */
        void onRefresh(Ts ts);

        /**
         * 添加多条
         * @param ts 数据类型
         * @param position
         */
        void onLoad(Ts ts, int position);

        /**
         * 插入
         * @param t  数据类型
         * @param position 位置
         *                 该方法可以和add合并
         */
        void insertItem(T t, int position);

        /**
         * 删除
         * @param position 位置
         */
        void deleteItem(int position);

        /**
         * 替换
         * @param t 数据类型
         * @param position
         */
        void replaceItem(T t, int position);

        /**
         * 获取数据
         * @param position  位置
         * @return 数据类型
         */
        T getItem(int position);
    }
}
