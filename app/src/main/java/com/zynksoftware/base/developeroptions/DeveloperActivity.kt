package com.zynksoftware.base.developeroptions

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ActivityDeveloperBinding
import com.zynksoftware.base.ui.common.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import java.io.File


class DeveloperActivity :
    BaseActivity<ActivityDeveloperBinding>(ActivityDeveloperBinding::inflate) {
    private val logProvider:LogProvider by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val developerViewModel: DeveloperViewModel by viewModel()
        val versionNameString=getString(R.string.version_name) + developerViewModel.getAppVersion()
        binding.versionNameTextView.text =versionNameString

        binding.deviceDetailsValueTextView.text = developerViewModel.getSystemDetail(resources.displayMetrics.densityDpi)

        val switch = binding.keepScreenOnOffSwitch
        initKeepScreenOnOffSwitch(switch)
        initLogsOnOffSwitch(binding.logsOnOffSwitch)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initLogsOnOffSwitch(switch: SwitchCompat) {
        //open shared pref and put state of switch
        val sharedPreferences = getSharedPreferences("com.zynksoftware.base" ,MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        switch.isChecked = sharedPreferences.getBoolean(getString(R.string.logSwitchSP),true)
        //if state is on start the ProviderLog thread
        if (switch.isChecked){
            deleteFilesBeforeStartingLogs()
            logProvider.start()
        }
        switch.setOnCheckedChangeListener { _, isChecked ->
            val message: String
            if (isChecked) {
                deleteFilesBeforeStartingLogs()
                logProvider.start()
                message = getString(R.string.logs_enabled)
                //Log.v("ZynkTag", "Log test")
                //put true in shared pref for log switch
                editor.putBoolean(getString(R.string.logSwitchSP),true)
                editor.apply()
            } else {
                logProvider.interrupt()
                message = getString(R.string.logs_disabled)
                //put false in shared pref for log switch
                editor.putBoolean(getString(R.string.logSwitchSP),false)
                editor.apply()
            }
            Toast.makeText(
                applicationContext, message,
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    //delete files in logDir before writing the new ones
    private fun deleteFilesBeforeStartingLogs() {
        val dirPath = filesDir.absolutePath + File.separator.toString() + getString(R.string.baseLogDirName)
        val projDir = File(dirPath)
        if(projDir.listFiles()!=null)
            for (child in projDir.listFiles()) child.delete()
    }

    private fun initKeepScreenOnOffSwitch(switch: SwitchCompat) {
        val sharedPreferences = getSharedPreferences("com.zynksoftware.base" ,MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        switch.isChecked = sharedPreferences.getBoolean(getString(R.string.screenSwitchSP),true)
        if (switch.isChecked){
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        switch.setOnCheckedChangeListener { _, isChecked ->

            val message: String
            if (isChecked) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                message = getString(R.string.screen_on_enabled)
                //Log.v("ZynkTag", "Log Test")
                //put true in shared pref for screen switch
                editor.putBoolean(getString(R.string.screenSwitchSP),true)
                editor.apply()
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                message = getString(R.string.screen_on_disabled)
                //put false in shared pref for screen switch
                editor.putBoolean(getString(R.string.screenSwitchSP),false)
                editor.apply()
            }
            Toast.makeText(
                applicationContext, message,
                Toast.LENGTH_SHORT
            ).show()

        }
    }


    override fun getViewIdToFindNavController(): Int {
        TODO("Not yet implemented")
    }


}