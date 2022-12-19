package com.zynksoftware.base.ui.pager

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.FragmentPagerDashboardBinding
import com.zynksoftware.base.extensions.observe
import com.zynksoftware.base.ui.common.BaseFragment
import com.zynksoftware.base.ui.common.SharedViewModel
import com.zynksoftware.base.ui.pager.components.NavigationModel
import com.zynksoftware.base.ui.pager.components.OnPageChangedListener

class PagerDashboardFragment :
    BaseFragment<FragmentPagerDashboardBinding>(FragmentPagerDashboardBinding::inflate) {

    private val pagerDashboardViewModel: PagerDashboardViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun FragmentPagerDashboardBinding.onViewCreated(savedInstanceState: Bundle?) {
        bottomNavigation.setTabSelectedListener(object: OnPageChangedListener {
            override fun onTabSelected(position: Int) {

            }
        })

        observe(pagerDashboardViewModel.tabPositionLiveData){

        }

        val navigationModel = arrayListOf(
            NavigationModel(Fragment1.newInstance(), R.drawable.ic_send_money_unfocused, R.drawable.ic_send_money_focused),
            NavigationModel(Fragment2.newInstance(), R.drawable.ic_dashboard_unfocused, R.drawable.ic_dashboard_focused),
            NavigationModel(Fragment3.newInstance(), R.drawable.ic_bank, R.drawable.ic_bank_focused),
        )
        bottomNavigation.initialize(
            viewPager, requireActivity(), navigationModel, pagerDashboardViewModel.tabPositionLiveData
        )
    }
}