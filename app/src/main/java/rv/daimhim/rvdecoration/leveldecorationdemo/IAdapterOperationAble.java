package rv.daimhim.rvdecoration.leveldecorationdemo;

/**
 * 项目名称：com.example.used.baseadapter
 * 项目版本：usedlibrary
 * 创建人：Daimhim
 * 创建时间：2017/10/26 15:30
 * 修改人：Daimhim
 * 修改时间：2017/10/26 15:30
 * 类描述：Adapter一些基本方法
 * 修改备注：
 * @author Daimhim
 */

public interface IAdapterOperationAble<Ts,T> {
    /**
     * 刷新
     * @param ts 数据类型
     */
    void onRefresh(Ts ts);

    /**
     * 添加多条
     * @param ts 数据类型
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
