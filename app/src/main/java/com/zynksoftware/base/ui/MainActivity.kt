package com.zynksoftware.base.ui

import android.os.Bundle
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ActivityMainBinding
import com.zynksoftware.base.common.extensions.observe
import com.zynksoftware.base.ui.common.BaseActivity
import com.zynksoftware.base.utils.device.DeviceUtils
import com.zynksoftware.base.utils.security.SecurityUtils
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun getViewIdToFindNavController(): Int = R.id.dashboard_nav_host_fragment

    private val securityUtils: SecurityUtils by inject { parametersOf(this) }
    private val deviceUtils: DeviceUtils by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        deviceUtils.saveScreenResolution(this)

        /*if (!securityUtils.checkSecurity()) {
            return
        }*/

        observe(sharedViewModel.isLoading){
            binding.loadingComponent.setIsLoading(it)
        }
    }

}