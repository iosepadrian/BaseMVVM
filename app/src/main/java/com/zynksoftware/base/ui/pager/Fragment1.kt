package com.zynksoftware.base.ui.pager

import android.os.Bundle
import android.view.View
import com.zynksoftware.base.BuildConfigUtils
import com.zynksoftware.base.common.extensions.makeLinks
import com.zynksoftware.base.databinding.FragmentDemo1Binding
import com.zynksoftware.base.developeroptions.ui.developer.DeveloperActivity
import com.zynksoftware.base.ui.common.BaseFragment
import com.zynksoftware.base.ui.common.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Fragment1 : BaseFragment<FragmentDemo1Binding>(FragmentDemo1Binding::inflate) {

    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }

    @Inject
    lateinit var buildConfigUtils: BuildConfigUtils

    override fun getVM() = BaseViewModel()

    override fun FragmentDemo1Binding.onViewCreated(savedInstanceState: Bundle?) {
        if (buildConfigUtils.shouldShowDeveloperOption()) {
            binding?.developerActivityButton?.visibility = View.VISIBLE
            val clickableText = "here"
            binding?.developerActivityButton?.makeLinks(
                textSize = binding?.developerActivityButton!!.textSize + 20,
                links = arrayOf(Pair(clickableText) {
                    DeveloperActivity.start(requireActivity())
                })
            )
        } else {
            binding?.developerActivityButton?.visibility = View.GONE
        }
    }
}