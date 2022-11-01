package com.zynksoftware.base.developeroptions.recyclerview.adapters

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.zynksoftware.base.databinding.ViewHolderDemoBinding
import com.zynksoftware.base.models.DemoModel
import com.zynksoftware.base.ui.common.recyclerview.BaseAdapter

class DemoAdapter(
    private val itemClick: (DemoModel) -> Unit? = {}
): BaseAdapter<DemoModel, ViewHolderDemoBinding>(ViewHolderDemoBinding::inflate, Comparator) {

    override fun ViewHolderDemoBinding.onBind(context: Context, item: DemoModel, position: Int) {
        textView.text = item.title
        rootView.setOnClickListener {
            itemClick.invoke(item)
        }
    }

    object Comparator : DiffUtil.ItemCallback<DemoModel>() {
        override fun areItemsTheSame(oldItem: DemoModel, newItem: DemoModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DemoModel, newItem: DemoModel) =
            oldItem.title == newItem.title
    }
}