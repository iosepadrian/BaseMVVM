package com.zynksoftware.base.ui.common.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class PagingBaseAdapter<T : Any, VB: ViewBinding>(
    var bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    comparator: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, PagingBaseViewHolder<T, VB>>(comparator) {

    abstract fun VB.onBind(item: T?, context: Context, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingBaseViewHolder<T, VB> {
        val viewBinding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return PagingBaseViewHolder(viewBinding) { item, position ->
            viewBinding.onBind(item, parent.context, position)
        }
    }

    override fun onBindViewHolder(holder: PagingBaseViewHolder<T, VB>, position: Int) {
        holder.bind(getItem(position), position)
    }
}

class PagingBaseViewHolder<T, VB: ViewBinding> internal constructor(
    binding: VB,
    private val expression: (item: T?, position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T?, position: Int) {
        expression(item, position)
    }
}