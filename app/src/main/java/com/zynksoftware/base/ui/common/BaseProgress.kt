package com.zynksoftware.base.ui.common

import android.os.Bundle
import android.view.ViewGroup
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ComponentLoadingLayoutBinding

class BaseProgress : BaseDialogFragment<ComponentLoadingLayoutBinding>(ComponentLoadingLayoutBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LoadingDialog)
        isCancelable = false
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }

    override fun ComponentLoadingLayoutBinding.onViewCreated(savedInstanceState: Bundle?) {}
}