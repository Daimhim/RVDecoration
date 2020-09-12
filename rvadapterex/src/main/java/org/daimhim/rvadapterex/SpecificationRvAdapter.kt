package org.daimhim.rvadapterex

import org.daimhim.rvadapter.RecyclerContract

abstract class SpecificationRvAdapter<T> : SimpleRvAdapter<T>(),
    RecyclerContract.SpecificationContract<MutableList<T>,T> {

    override fun insertItem(t: T, position: Int) {
        data.add(position,t)
        notifyItemInserted(position)
    }

    override fun deleteItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun replaceItem(t: T, position: Int) {
        data[position] = t
        notifyItemChanged(position,t)
    }
}