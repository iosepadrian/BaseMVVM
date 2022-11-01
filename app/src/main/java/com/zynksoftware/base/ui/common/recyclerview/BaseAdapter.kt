package com.zynksoftware.base.ui.common.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB: ViewBinding>(
    var bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    comparator: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<T, VB>>(comparator) {

    abstract fun VB.onBind(context: Context, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> {
        val viewBinding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(viewBinding) { item, position ->
            viewBinding.onBind(parent.context, item, position)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        holder.bind(getItem(position), position)
    }
}

class BaseViewHolder<T, VB: ViewBinding> internal constructor(
    binding: VB,
    private val expression: (item: T, position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T, position: Int) {
        expression(item, position)
    }
}