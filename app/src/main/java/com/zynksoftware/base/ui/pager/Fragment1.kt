package com.zynksoftware.base.ui.pager

import android.os.Bundle
import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.common.extensions.makeLinks
import com.zynksoftware.base.databinding.FragmentDemo1Binding
import com.zynksoftware.base.developeroptions.DeveloperActivity
import com.zynksoftware.base.ui.common.BaseFragment
import com.zynksoftware.base.ui.common.BaseViewModel

class Fragment1 : BaseFragment<FragmentDemo1Binding>(FragmentDemo1Binding::inflate) {

    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }

    override fun getVM() = BaseViewModel()

    override fun FragmentDemo1Binding.onViewCreated(savedInstanceState: Bundle?) {
        val clickableText = "here"
        binding?.developerActivityButton?.makeLinks(
            textSize = binding?.developerActivityButton!!.textSize + 20,
            links = arrayOf(Pair(clickableText) {
                DeveloperActivity.start(requireActivity())
            })
        )
        binding?.textViewVersion?.text = "${BuildConfig.VERSION_NAME} - ${BuildConfig.VERSION_CODE}"
    }
}