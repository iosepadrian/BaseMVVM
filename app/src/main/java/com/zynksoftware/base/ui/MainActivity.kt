package com.zynksoftware.base.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ActivityMainBinding
import com.zynksoftware.base.ui.common.BaseActivity
import com.zynksoftware.base.utils.device.DeviceUtils
import com.zynksoftware.base.utils.security.SecurityUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.util.Log

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()

    override fun getViewIdToFindNavController(): Int = R.id.dashboard_nav_host_fragment
    override fun getVM(): MainViewModel = viewModel

    @Inject lateinit var securityUtils: SecurityUtils
    @Inject lateinit var deviceUtils: DeviceUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        deviceUtils.saveScreenResolution(this)

        if (!securityUtils.checkSecurity()) {
            Log.d("CheckSecurity", "Not secure")
        }
    }

}