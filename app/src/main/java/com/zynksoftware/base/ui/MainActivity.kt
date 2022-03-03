package com.zynksoftware.base.ui

import android.os.Bundle
import com.zynksoftware.base.databinding.ActivityMainBinding
import com.zynksoftware.base.ui.common.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun getViewIdToFindNavController(): Int {
        //TODO
        return -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel.isLoading.observe(this) {
            binding.loadingComponent.setIsLoading(it)
        }

        sharedViewModel.setIsLoading(true)
    }

}