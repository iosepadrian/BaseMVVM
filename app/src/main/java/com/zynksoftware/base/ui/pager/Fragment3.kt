package com.zynksoftware.base.ui.pager

import android.os.Bundle
import com.zynksoftware.base.databinding.FragmentDemoBinding
import com.zynksoftware.base.ui.common.BaseFragment

class Fragment3: BaseFragment<FragmentDemoBinding>(FragmentDemoBinding::inflate) {

    companion object {
        fun newInstance(): Fragment3 {
            return Fragment3()
        }
    }

    override fun FragmentDemoBinding.onViewCreated(savedInstanceState: Bundle?) {
        fragmentTitleTextView.text = "FRAGMENT 3"
    }
}