package com.zynksoftware.base.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.Delegates

abstract class BaseAdapter<T, VB: ViewBinding>(
    list: MutableList<T>,
    var bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : RecyclerView.Adapter<BaseViewHolder<T, VB>>(), AutoUpdatableAdapter {

    abstract fun VB.onBind(item: T, context: Context)

    private var listOfItems: MutableList<T> by Delegates.observable(list) { prop, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> {
        val viewBinding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(viewBinding, { item ->
            viewBinding.onBind(item, parent.context)
        })
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        holder.bind(listOfItems[position])
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    fun updateList(newItems: MutableList<T>) {
        listOfItems = newItems
    }
}

class BaseViewHolder<T, VB: ViewBinding> internal constructor(
    binding: VB,
    private val expression: (item: T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        expression(item)
    }
}

interface AutoUpdatableAdapter {

    fun <T> RecyclerView.Adapter<*>.autoNotify(old: MutableList<T>, new: MutableList<T>, compare: (T, T) -> Boolean) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compare(old[oldItemPosition], new[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == new[newItemPosition]
            }

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        })

        diff.dispatchUpdatesTo(this)
    }
}