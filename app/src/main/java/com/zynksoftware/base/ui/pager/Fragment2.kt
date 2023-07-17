package com.zynksoftware.base.ui.pager

import android.os.Bundle
import androidx.compose.material.Button
import androidx.compose.material.Text
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.FragmentDemoBinding
import com.zynksoftware.base.ui.DemoDialogFragment
import com.zynksoftware.base.ui.common.BaseFragment
import com.zynksoftware.base.ui.common.BaseViewModel


class Fragment2 : BaseFragment<FragmentDemoBinding>(FragmentDemoBinding::inflate) {

    companion object {
        fun newInstance(): Fragment2 {
            return Fragment2()
        }
    }
    override fun getVM() = BaseViewModel()

    override fun FragmentDemoBinding.onViewCreated(savedInstanceState: Bundle?) {
        fragmentTitleTextView.text = getString(R.string.fragmet_2)
        dialogFragmentButton.setContent {
            Button(onClick = {
                val dialog = DemoDialogFragment.newInstance()
                dialog.show(requireActivity().supportFragmentManager, "Demo Dialog")
            }) {
                Text(text = "Press to open dialog fragment")
            }
        }
    }
}