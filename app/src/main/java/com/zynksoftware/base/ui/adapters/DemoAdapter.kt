package com.zynksoftware.base.ui.adapters

import android.content.Context
import com.zynksoftware.base.databinding.ViewHolderDemoBinding
import com.zynksoftware.base.models.DemoModel
import com.zynksoftware.base.ui.common.BaseAdapter

class DemoAdapter(
    items: MutableList<DemoModel>
): BaseAdapter<DemoModel, ViewHolderDemoBinding>(items, ViewHolderDemoBinding::inflate) {

    override fun ViewHolderDemoBinding.onBind(item: DemoModel, context: Context) {
        textView.text = item.title
    }
}