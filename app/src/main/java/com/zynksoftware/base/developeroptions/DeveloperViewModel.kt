package com.zynksoftware.base.developeroptions

import android.annotation.SuppressLint
import android.os.Build
import android.util.DisplayMetrics
import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.R
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.utils.StringResourceProvider

class DeveloperViewModel(
    private val stringResourceProvider: StringResourceProvider
) : BaseViewModel() {


    fun getAppVersion() :String{
        val versionCode= BuildConfig.VERSION_CODE
        val versionName= BuildConfig.VERSION_NAME
        return "$versionName ( $versionCode )"

    }

    private fun getDeviceDensityString(densityDpi:Int): String? {
        when (densityDpi) {
            DisplayMetrics.DENSITY_LOW -> return stringResourceProvider.getString(R.string.ldpi)
            DisplayMetrics.DENSITY_MEDIUM -> return stringResourceProvider.getString(R.string.mdpi)
            DisplayMetrics.DENSITY_TV, DisplayMetrics.DENSITY_HIGH -> return stringResourceProvider.getString(R.string.hdpi)
            DisplayMetrics.DENSITY_260, DisplayMetrics.DENSITY_280, DisplayMetrics.DENSITY_300, DisplayMetrics.DENSITY_XHIGH -> return stringResourceProvider.getString(R.string.xhdpi)
            DisplayMetrics.DENSITY_340, DisplayMetrics.DENSITY_360, DisplayMetrics.DENSITY_400, DisplayMetrics.DENSITY_420, DisplayMetrics.DENSITY_440, DisplayMetrics.DENSITY_XXHIGH -> return stringResourceProvider.getString(R.string.xxhdpi)
            DisplayMetrics.DENSITY_560, DisplayMetrics.DENSITY_XXXHIGH -> return stringResourceProvider.getString(R.string.xxxhdpi)
        }
        return null
    }

    @SuppressLint("HardwareIds")
    fun getSystemDetail(densityDpi:Int): String {
        return stringResourceProvider.getString(R.string.brand)+ Build.BRAND.toString() + stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.screen_type) + getDeviceDensityString(densityDpi) +stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.model)+ Build.MODEL.toString() + stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.id)+ Build.ID.toString() + stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.sdk)+ Build.VERSION.SDK_INT.toString()+ stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.manufacture)+ Build.MANUFACTURER.toString() +stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.brand)+ Build.BRAND.toString()+stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.user)+ Build.USER.toString()+stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.type)+ Build.TYPE.toString()+stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.base)+ Build.VERSION_CODES.BASE.toString()+stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.incremental)+ Build.VERSION.INCREMENTAL.toString()+stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.board)+ Build.BOARD.toString()+stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.host)+ Build.HOST.toString()+stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.fingerprint)+ Build.FINGERPRINT.toString()+stringResourceProvider.getString(R.string.new_line)+
                stringResourceProvider.getString(R.string.version_code)+ Build.VERSION.RELEASE.toString()
    }


}