package com.zynksoftware.base.ui

import android.os.Bundle
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.FragmentDemoDialogBinding
import com.zynksoftware.base.ui.common.BaseDialogFragment

class DemoDialogFragment : BaseDialogFragment<FragmentDemoDialogBinding>(FragmentDemoDialogBinding::inflate) {

    companion object {
        fun newInstance(): DemoDialogFragment {
            return DemoDialogFragment()
        }
    }

    override fun FragmentDemoDialogBinding.onViewCreated(savedInstanceState: Bundle?) {
        demoTextView.text=getString(R.string.demo_fragment)
    }
}