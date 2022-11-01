package com.zynksoftware.base.ui.pager

import android.os.Bundle
import com.zynksoftware.base.databinding.FragmentDemo1Binding
import com.zynksoftware.base.developeroptions.DeveloperActivity
import com.zynksoftware.base.ui.common.BaseFragment

class Fragment1: BaseFragment<FragmentDemo1Binding>(FragmentDemo1Binding::inflate) {

    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }

    override fun FragmentDemo1Binding.onViewCreated(savedInstanceState: Bundle?) {
        developerActivityButton.setOnClickListener {
            DeveloperActivity.start(requireActivity())
        }
    }
}