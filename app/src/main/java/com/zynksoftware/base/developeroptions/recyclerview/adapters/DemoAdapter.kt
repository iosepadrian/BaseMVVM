package com.zynksoftware.base.developeroptions.recyclerview.adapters

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.zynksoftware.base.databinding.ViewHolderDemoBinding
import com.zynksoftware.base.models.CryptoModel
import com.zynksoftware.base.ui.common.recyclerview.BaseAdapter

class DemoAdapter(
    private val itemClick: (CryptoModel) -> Unit? = {}
): BaseAdapter<CryptoModel, ViewHolderDemoBinding>(ViewHolderDemoBinding::inflate, Comparator) {

    override fun ViewHolderDemoBinding.onBind(context: Context, item: CryptoModel, position: Int) {
        textView.text = item.name
        rootView.setOnClickListener {
            itemClick.invoke(item)
        }
    }

    object Comparator : DiffUtil.ItemCallback<CryptoModel>() {
        override fun areItemsTheSame(oldItem: CryptoModel, newItem: CryptoModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CryptoModel, newItem: CryptoModel) =
            oldItem.name == newItem.name
    }
}