package com.zynksoftware.base.developeroptions

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Switch
import android.widget.Toast
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ActivityDeveloperBinding
import com.zynksoftware.base.ui.common.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class DeveloperActivity : BaseActivity<ActivityDeveloperBinding>(ActivityDeveloperBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val developerViewModel: DeveloperViewModel by viewModel()
        binding.versionNameTextView.text=  getString(R.string.version_name) + developerViewModel.getAppVersion()
        binding.deviceDetailsValueTextView.text=getSystemDetail()
        binding.screenTypeTextView.text=getString(R.string.screen_type) + getDeviceDensityString()
        val switch = binding.keepScreenOnOffSwitch
        initKeepScreenOnOffSwitch(switch)
    }

    private fun initKeepScreenOnOffSwitch(switch: Switch){
        switch.setOnCheckedChangeListener { _, isChecked ->

            val message: String = if (isChecked) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                "Keep screen on enabled"
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                "Keep screen on disabled"
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

    // not passing context to viewmodel because of memory leak
    private fun getDeviceDensityString(): String? {
        when (resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> return getString(R.string.ldpi)
            DisplayMetrics.DENSITY_MEDIUM -> return getString(R.string.mdpi)
            DisplayMetrics.DENSITY_TV, DisplayMetrics.DENSITY_HIGH -> return getString(R.string.hdpi)
            DisplayMetrics.DENSITY_260, DisplayMetrics.DENSITY_280, DisplayMetrics.DENSITY_300, DisplayMetrics.DENSITY_XHIGH -> return getString(R.string.xhdpi)
            DisplayMetrics.DENSITY_340, DisplayMetrics.DENSITY_360, DisplayMetrics.DENSITY_400, DisplayMetrics.DENSITY_420, DisplayMetrics.DENSITY_440, DisplayMetrics.DENSITY_XXHIGH -> return getString(R.string.xxhdpi)
            DisplayMetrics.DENSITY_560, DisplayMetrics.DENSITY_XXXHIGH -> return getString(R.string.xxxhdpi)
        }
        return null
    }

    @SuppressLint("HardwareIds")
    private fun getSystemDetail(): String {
        return getString(R.string.brand)+ Build.BRAND.toString() + getString(R.string.new_line)+
                getString(R.string.deviceID)+
                    Settings.Secure.getString(
                        contentResolver,
                        Settings.Secure.ANDROID_ID
                    )+ getString(R.string.new_line)+
                getString(R.string.model)+ Build.MODEL.toString() + getString(R.string.new_line)+
                getString(R.string.id)+ Build.ID.toString() + getString(R.string.new_line)+
                getString(R.string.sdk)+Build.VERSION.SDK_INT.toString()+ getString(R.string.new_line)+
                getString(R.string.manufacture)+Build.MANUFACTURER.toString() +getString(R.string.new_line)+
                getString(R.string.brand)+Build.BRAND.toString()+getString(R.string.new_line)+
                getString(R.string.user)+Build.USER.toString()+getString(R.string.new_line)+
                getString(R.string.type)+Build.TYPE.toString()+getString(R.string.new_line)+
                getString(R.string.base)+Build.VERSION_CODES.BASE.toString()+getString(R.string.new_line)+
                getString(R.string.incremental)+Build.VERSION.INCREMENTAL.toString()+getString(R.string.new_line)+
                getString(R.string.board)+Build.BOARD.toString()+getString(R.string.new_line)+
                getString(R.string.host)+Build.HOST.toString()+getString(R.string.new_line)+
                getString(R.string.fingerprint)+Build.FINGERPRINT.toString()+getString(R.string.new_line)+
                getString(R.string.version_code)+Build.VERSION.RELEASE.toString()
    }


}