package org.daimhim.rvadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名称：org.daimhim.rvadapter
 * 项目版本：MeykiHelper-fp-v1.6.7
 * 创建人：Daimhim
 * 创建时间：2017/12/24 16:30
 * 修改人：Daimhim
 * 修改时间：2017/12/24 16:30
 * 类描述：
 * 修改备注：
 *
 * @author Daimhim
 */

public abstract class RecyclerViewClickAdapter extends RecyclerView.Adapter<RecyclerViewClickAdapter.ClickViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    @Override
    public void onBindViewHolder(ClickViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    /**
     * 返回<code> position </ code>处的项目视图类型
     * 视图回收。
     * <p>
     * <p>这个方法的默认实现返回0，做出了假设
     * 适配器的单一视图类型。 与ListView适配器不同，类型不需要
     * 是连续的。 考虑使用id资源来唯一标识项目视图类型。
     *
     * @param位置查询
     * @return整数值标识需要代表项目的视图的类型 <code> position </ code>。 类型代码不需要是连续的。
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     *   *表示数据集中的每个项目是否可以用唯一标识符表示
     *   *类型{@link Long}。
     *   *
     *   * @参数hasStableIds数据集中的项目是否具有唯一标识符。
     *   * @see #hasStableIds（）
     *   * @see #getItemId（int）
     *  
     */
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    /**
     * 在<code> position </ code>处返回项目的稳定ID。 如果{@link #hasStableIds（）}
     * 会返回false，这个方法应该返回{@link #NO_ID}。 默认的实现
     * 此方法返回{@link #NO_ID}。
     *
     * @param位置查询的适配器位置
     * @返回位置上物品的稳定ID
     */
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     *   *当此适配器创建的视图已被回收时调用。
     *  *
     *   *当一个布局管理器不再决定它的时候，一个视图被回收
     *   *需要附加到其父母{@link RecyclerView}。 这可能是因为它
     *   *失去了可见性或由视图表示的一组缓存视图
     *   *附加到父RecyclerView。 如果项目视图有大量或昂贵的数据
     *   *绑定它，如大的位图，这可能是一个很好的地方释放这些
     *   *资源。</ p>
     *   * <p>
     *   RecyclerView在清除ViewHolder的内部数据之前调用这个方法
     *   *发送给RecycledViewPool。 这样，如果ViewHolder持有有效的信息
     *   *在回收之前，您可以调用{@link ViewHolder＃getAdapterPosition（）}来获取
     *   *其适配器位置。
     *  *
     *   * @param holder ViewHolder用于回收视图
     *  
     */
    @Override
    public void onViewRecycled(ClickViewHolder holder) {
        super.onViewRecycled(holder);
    }

    /**
     * 如果由此适配器创建的ViewHolder无法回收，则由RecyclerView调用
     *      *由于其瞬态状态。在收到回调后，Adapter可以清除
     *      *影响视图的瞬态状态的动画，并返回<code> true </ code>
     * 视图可以回收。请记住，该视图已被删除
     *      * RecyclerView。
     * <p>
     *      *在某些情况下，虽然它有暂态，但回收一个视图是可以接受的。最
     *      *的时候，这是一个情况下，瞬态将被清除
     *      *当View被反弹到一个新的位置时，调用{@link #onBindViewHolder（ViewHolder，int）}。
     * 由于这个原因，RecyclerView将决定留给适配器并使用返回
     *      *这个方法的值决定View是否应该被回收。
     * <p>
     *      *请注意，所有动画由{@link RecyclerView.ItemAnimator}创建时，您
     *      *永远不会收到这个回调，因为RecyclerView将这些视图保存为子视图
     *      *直到他们的动画完成。这个回调在项目的子项时很有用
     *      *视图创建的动画可能不容易使用{@link ItemAnimator}实现。
     * <p>
     *      *您应该永远</ em>通过调用来解决这个问题
     *      * <code> holder.itemView.setHasTransientState（false）; </ code>除非您之前调用过
     *      * <code> holder.itemView.setHasTransientState（true）; </ code>。每
     *      * <code> View.setHasTransientState（true）</ code>调用必须与a匹配
     *      * <code> View.setHasTransientState（false）</ code>调用，否则，View的状态
     *      *可能会变得不一致。你应该总是喜欢结束或取消动画
     *      *触发瞬态而不是手动处理。
     *
     * @参数持有者包含无法回收的视图的ViewHolder由于其      *瞬态。
     * @return如果View被回收，则返回true，否则返回false。请注意，如果这个方法      *返回<code> true </ code>，RecyclerView <em>会忽略</ em>的瞬态状态
     *      *查看和回收它无论如何。如果此方法返回<code> false </ code>，
     * RecyclerView将在做出最终决定之前再次检查View的瞬态状态。
     * 默认实现返回false。
     */
    @Override
    public boolean onFailedToRecycleView(ClickViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    /**
     * 当此适配器创建的视图已附加到窗口时调用。
     * <p>
     * <p>这可以作为一个合理的信号，视图即将被看到
     * 由用户。 如果适配器以前释放了任何资源
     * {@link #onViewDetachedFromWindow（RecyclerView.ViewHolder）onViewDetachedFromWindow}
     * 这些资源应该在这里恢复。</ p>
     *
     * @param持有人持有的观点持有人
     */
    @Override
    public void onViewAttachedToWindow(ClickViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    /**
     * 当此适配器创建的视图从窗口中分离时调用。
     * <p>
     * 脱离窗口不一定是永久的条件;
     * 适配器视图的使用者可能会选择在屏幕上缓存视图
     * 不可见，根据情况附加和分离它们。</ p>
     *
     * @param持有人分离的观点持有人
     */
    @Override
    public void onViewDetachedFromWindow(ClickViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    /**
     *   *注册一个新的观察者来监听数据变化。
     *  *
     *   * <p>适配器可以发布描述特定更改的各种事件。
     *   *并非所有的适配器都可以支持所有的更改类型，有些可能会回到通用的
     *   * {@link android.support.v7.widget.RecyclerView.AdapterDataObserver＃onChanged（）
     *   *如果更具体的数据不可用，“事情发生了变化”}事件。</ p>
     *  *
     *   * <p>使用适配器注册观察者的组件负责
     *   * {@link #unregisterAdapterDataObserver（RecyclerView.AdapterDataObserver）
     *   *完成后取消注册这些观察者。</ p>
     *  *
     *   * @param观察员观察员注册
     *  *
     *   * @see #unregisterAdapterDataObserver（RecyclerView.AdapterDataObserver）
     *          
     */
    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    /**
     * 注销当前正在侦听数据更改的观察者。
     * <p>
     * <p>未注册的观察者将不再接收有关更改的事件
     * 到适配器。</ p>
     *
     * @param观察员观察员注销
     * @see #registerAdapterDataObserver（RecyclerView.AdapterDataObserver）
     */
    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
    }

    /**
     * 当开始观察这个适配器时，由RecyclerView调用。
     * <p>
     * 请记住，同一个适配器可能会被多个RecyclerViews观察到。
     *
     * @param recyclerView开始观察这个适配器的RecyclerView实例。
     * @see #onDetachedFromRecyclerView（RecyclerView）
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * 当它停止观察这个适配器时，由RecyclerView调用。
     *
     * @param recyclerView停止观察此适配器的RecyclerView实例。
     * @see #onAttachedToRecyclerView（RecyclerView）
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    /**
     *  *当RecyclerView需要一个新的给定类型的ViewHolder来表示时调用
     *      * 一个物品。
     * <p>
     *      *这个新的ViewHolder应该用一个可以代表项目的新视图来构建
     *      *给定的类型。您可以手动创建一个新的视图，也可以通过XML来扩充它
     *      *布局文件。
     * <p>
     *      *新的ViewHolder将用于显示适配器使用的项目
     *      * {@link #onBindViewHolder（ViewHolder，int，List）}。由于它将被重新使用来显示
     *      *数据集中的不同项目，缓存对子视图的引用是一个好主意
     *      *视图以避免不必要的{@link View＃findViewById（int）}调用。
     *  *
     *  * @param parent新的视图绑定后添加到的ViewGroup
     *      *适配器位置。
     *  * @param viewType新视图的视图类型。
     *  *
     *  * @return保存给定视图类型视图的新ViewHolder。
     *  * @see #getItemViewType（int）
     *  * @see #onBindViewHolder（ViewHolder，int）
     *  
     */
    @Override
    public ClickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ClickViewHolder holder, int position) {
        holder.itemView.setOnClickListener(null);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public static class ClickViewHolder extends RecyclerView.ViewHolder {
        View.OnClickListener mOnClickListener;
        View.OnLongClickListener mOnLongClickListener;
        RecyclerViewClickAdapter mRecyclerViewClickAdapter;
        public ClickViewHolder(View itemView) {
            super(itemView);
        }
        public boolean performItemClick(View view,RecyclerViewClickAdapter recyclerViewClickAdapter){
            if (mOnClickListener == null){
                mRecyclerViewClickAdapter = recyclerViewClickAdapter;
                mOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerViewClickAdapter.mOnItemClickListener.onItemClick(v,getLayoutPosition());
                    }
                };
            }else {
                view.setOnClickListener(mOnClickListener);
            }
            return true;
        }
        public boolean performLongItemClick(View view,RecyclerViewClickAdapter recyclerViewClickAdapter){
            if (mOnLongClickListener == null){
                mRecyclerViewClickAdapter = recyclerViewClickAdapter;
                mOnLongClickListener = new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mRecyclerViewClickAdapter.mOnItemLongClickListener.onItemLongClick(v,getLayoutPosition());
                        return false;
                    }
                };
            }else {
                view.setOnLongClickListener(mOnLongClickListener);
            }
            return true;
        }
    }
    /**
     * 提供给外部的 接口  传入此接口 监听点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
    public interface RecyclerAdapterContract<T extends List<?>, B> {
        void onRefresh(T t);

        B getItem(int position);
    }
}
