package com.zynksoftware.base.developeroptions

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.widget.SwitchCompat
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ActivityDeveloperBinding
import com.zynksoftware.base.ui.common.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject

class DeveloperActivity :
    BaseActivity<ActivityDeveloperBinding>(ActivityDeveloperBinding::inflate) {
    private val logProvider: LogProvider by inject()
    private val developerViewModel: DeveloperViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val versionNameString =
            getString(R.string.version_name) + developerViewModel.getAppVersion()
        binding.versionNameTextView.text = versionNameString
        binding.deviceDetailsValueTextView.text =
            developerViewModel.getSystemDetail(resources.displayMetrics.densityDpi)
        val switch = binding.keepScreenOnOffSwitch
        initKeepScreenOnOffSwitch(switch)
        initLogsOnOffSwitch(binding.logsOnOffSwitch)
    }

    private fun initLogsOnOffSwitch(switch: SwitchCompat) {
        switch.isChecked = developerViewModel.getLogsSwitch()
        if (switch.isChecked) {
            DeveloperUtils.deleteFilesBeforeStartingLogs(applicationContext)
            logProvider.start()
        }
        switch.setOnCheckedChangeListener { _, isChecked ->
            val message: String
            if (isChecked) {
                DeveloperUtils.deleteFilesBeforeStartingLogs(applicationContext)
                logProvider.start()
                message = getString(R.string.logs_enabled)
                developerViewModel.setLogsSwitch(true)
            } else {
                logProvider.interrupt()
                message = getString(R.string.logs_disabled)
                developerViewModel.setLogsSwitch(false)
            }
            showToast(message)
        }
    }

    private fun initKeepScreenOnOffSwitch(switch: SwitchCompat) {
        switch.isChecked = developerViewModel.getScreenSwitch()
        if (switch.isChecked) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        switch.setOnCheckedChangeListener { _, isChecked ->
            val message: String
            if (isChecked) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                message = getString(R.string.screen_on_enabled)
                developerViewModel.setScreenSwitch(true)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                message = getString(R.string.screen_on_disabled)
                developerViewModel.setScreenSwitch(false)
            }
            showToast(message)
        }
    }

    override fun getViewIdToFindNavController(): Int {
        return -1
    }
}