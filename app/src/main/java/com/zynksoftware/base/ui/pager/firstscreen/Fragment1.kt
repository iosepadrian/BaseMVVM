package com.zynksoftware.base.ui.pager.firstscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zynksoftware.base.BuildConfigUtils
import com.zynksoftware.base.developeroptions.ui.developer.DeveloperActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Fragment1 : Fragment() {

    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }

    @Inject
    lateinit var buildConfigUtils: BuildConfigUtils

    private val viewModel: FirstScreenViewModel by viewModels()

    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        composeView.setContent {
            val state by viewModel.state.collectAsState()
            Fragment1Compose(
                state = state,
                onDeveloperButtonClick = { DeveloperActivity.start(requireActivity()) },
                shouldShowDevOptions = buildConfigUtils.shouldShowDeveloperOption()
            )
        }
    }
}