package com.zynksoftware.base.utils.device

import android.app.Activity
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics

class DeviceUtils(private val sharedPreferences: SharedPreferences) {

    companion object{
        private const val SCREEN_HEIGHT = "SCREEN_HEIGHT"
        private const val SCREEN_WIDTH = "SCREEN_WIDTH"
    }

    fun getDeviceDetails(): DeviceDetails {
        val deviceDetails = DeviceDetails()

        deviceDetails.appVersion = getAppVersionServer()
        deviceDetails.deviceBrand = Build.MANUFACTURER
        deviceDetails.deviceModel = Build.MODEL
        deviceDetails.operatingSystem = DeviceDetails.OperatingSystem.ANDROID
        deviceDetails.osVersion = Build.VERSION.RELEASE

        val resolution = "${getScreenWidth()} x ${getScreenHeight()}"
        deviceDetails.screenResolution = resolution

        return deviceDetails
    }

    fun saveScreenResolution(activity: Activity) {
        val metrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.display?.getRealMetrics(metrics)
        } else {
            @Suppress("DEPRECATION")
            activity.windowManager.defaultDisplay.getMetrics(metrics)
        }
        sharedPreferences.edit().putInt(SCREEN_WIDTH, metrics.widthPixels).apply()
        sharedPreferences.edit().putInt(SCREEN_HEIGHT, metrics.heightPixels).apply()
    }

    private fun getScreenHeight(): Int {
        var screenHeight = sharedPreferences.getInt(SCREEN_HEIGHT, 0)
        if (screenHeight == 0) {
            screenHeight = Resources.getSystem().displayMetrics.heightPixels
        }
        return screenHeight
    }

    private fun getScreenWidth(): Int {
        var screenWidth = sharedPreferences.getInt(SCREEN_WIDTH, 0)
        if (screenWidth == 0) {
            screenWidth = Resources.getSystem().displayMetrics.widthPixels
        }
        return screenWidth
    }

    private fun getAppVersionServer(): String {
        //TODO change format on server side to accept versioning like 1.2.1
        return "1.2"
    }

}