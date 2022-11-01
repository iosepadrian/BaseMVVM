package com.zynksoftware.base.developeroptions.recyclerview.adapters

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.zynksoftware.base.databinding.ViewHolderDemoBinding
import com.zynksoftware.base.models.DemoModel
import com.zynksoftware.base.ui.common.recyclerview.PagingBaseAdapter

class DemoInfiniteAdapter(
    private val itemClick: (DemoModel, Int) -> Unit,
    private val itemLongClick: (DemoModel, Int) -> Unit
): PagingBaseAdapter<DemoModel, ViewHolderDemoBinding>(ViewHolderDemoBinding::inflate, Comparator) {
    override fun ViewHolderDemoBinding.onBind(item: DemoModel?, context: Context, position: Int) {
        item?.let {
            textView.text = it.title
            rootView.setOnClickListener {
                itemClick.invoke(item, position)
            }
            rootView.setOnLongClickListener {
                itemLongClick.invoke(item, position)
                true
            }
        }
    }

    object Comparator : DiffUtil.ItemCallback<DemoModel>() {
        override fun areItemsTheSame(oldItem: DemoModel, newItem: DemoModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DemoModel, newItem: DemoModel) =
            oldItem.title == newItem.title
    }
}