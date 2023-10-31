package com.zynksoftware.base.developeroptions.ui.developer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ActivityDeveloperBinding
import com.zynksoftware.base.developeroptions.recyclerview.PagingActivity
import com.zynksoftware.base.developeroptions.recyclerview.SimpleRecyclerViewActivity
import com.zynksoftware.base.developeroptions.ui.ChangeEnvironmentFragment
import com.zynksoftware.base.developeroptions.utils.DeveloperUtils
import com.zynksoftware.base.developeroptions.utils.LogProvider
import com.zynksoftware.base.ui.common.BaseActivity
import com.zynksoftware.base.developeroptions.ui.testapi.TestApiFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DeveloperActivity : BaseActivity<ActivityDeveloperBinding>(ActivityDeveloperBinding::inflate),
    ChangeEnvironmentFragment.ChangeEnvironmentListener {
    @Inject
    lateinit var logProvider: LogProvider
    private val developerViewModel: DeveloperViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DeveloperActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getViewIdToFindNavController(): Int = -1

    override fun getVM() = developerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val versionNameString =
            getString(R.string.version_name) + developerViewModel.getAppVersion()
        binding.buildConfig.setSubtitleText(versionNameString)
        binding.deviceDetails.setSubtitleText(
            developerViewModel.getSystemDetail(resources.displayMetrics.densityDpi)
        )

        initKeepScreenOnOffSwitch()
        initLogsOnOffSwitch()
        initExportButton()

        binding.pagingRecycler.setOnClickListener {
            PagingActivity.start(this)
        }
        binding.simpleRecycler.setOnClickListener {
            SimpleRecyclerViewActivity.start(this)
        }
        binding.changeEnvironment.setOnClickListener {
            val changeEnvironmentFragment = ChangeEnvironmentFragment.newInstance()
            changeEnvironmentFragment.show(
                supportFragmentManager,
                ChangeEnvironmentFragment::class.java.simpleName
            )
            changeEnvironmentFragment.setChangeEnvironmentListener(this)
        }
        binding.testAPIButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, TestApiFragment.newInstance()).commit();
        }
    }

    private fun initExportButton() {
        binding.exportLogs.setOnClickListener {
            try {
                developerViewModel.sendEmail(this)
            } catch (t: Throwable) {
                showToast(getString(R.string.request_toast) + t.toString())
            }
        }
    }

    private fun initLogsOnOffSwitch() {
        binding.logsOnOff.setSwitchState(developerViewModel.getLogsSwitch())
        if (binding.logsOnOff.isSwitchChecked()) {
            DeveloperUtils.deleteFilesBeforeStartingLogs(applicationContext)
            logProvider.start()
        }
        binding.logsOnOff.setSwitchButtonClickListener { _, isChecked ->
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

    private fun initKeepScreenOnOffSwitch() {
        binding.keepScreenOn.setSwitchState(developerViewModel.getScreenSwitch())
        if (binding.keepScreenOn.isSwitchChecked()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        binding.keepScreenOn.setSwitchButtonClickListener { _, isChecked ->
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

    override fun getEnvironment(): String? {
        return developerViewModel.getEnvironment()
    }

    override fun getServerURL(): String? {
        return developerViewModel.getServerURL()
    }

    override fun setEnvironment(environment: String) {
        developerViewModel.setEnvironment(environment)
    }

    override fun setServerURL(url: String) {
        developerViewModel.setServerURL(url)
    }

    override suspend fun logout() {
        developerViewModel.logout()
    }
}