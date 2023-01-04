package com.zynksoftware.base.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ActivityMainBinding
import com.zynksoftware.base.common.extensions.observe
import com.zynksoftware.base.ui.common.BaseActivity
import com.zynksoftware.base.utils.device.DeviceUtils
import com.zynksoftware.base.utils.security.SecurityUtils
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()

    override fun getViewIdToFindNavController(): Int = R.id.dashboard_nav_host_fragment
    override fun getVM(): MainViewModel = viewModel

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