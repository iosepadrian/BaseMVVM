package com.zynksoftware.base.ui

import android.os.Bundle
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ActivityMainBinding
import com.zynksoftware.base.extensions.observe
import com.zynksoftware.base.ui.common.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun getViewIdToFindNavController(): Int = R.id.dashboard_nav_host_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe(sharedViewModel.isLoading){
            binding.loadingComponent.setIsLoading(it)
        }
    }

}