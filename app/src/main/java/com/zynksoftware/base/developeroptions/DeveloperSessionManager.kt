package com.zynksoftware.base.developeroptions

import android.content.SharedPreferences
import com.zynksoftware.base.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeveloperSessionManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val LOGS_SWITCH = "logSwitch"
        private const val SCREEN_SWITCH = "screenSwitch"
        private const val DEFAULT_ENVIRONMENT = "Default"
        private const val SERVER_URL = "serverURL"
        private const val DEVELOPER_ENVIRONMENT = "developerEnvironment"
    }

    fun setLogsSwitch(value: Boolean) {
        sharedPreferences.edit().putBoolean(LOGS_SWITCH, value).apply()
    }

    fun getLogsSwitch(): Boolean {
        return sharedPreferences.getBoolean(LOGS_SWITCH, false)
    }

    fun setScreenSwitch(value: Boolean) {
        sharedPreferences.edit().putBoolean(SCREEN_SWITCH, value).apply()
    }

    fun getScreenSwitch(): Boolean {
        return sharedPreferences.getBoolean(SCREEN_SWITCH, false)
    }

    fun setEnvironment(value: String) {
        sharedPreferences.edit().putString(DEVELOPER_ENVIRONMENT, value).commit() //use commit instead of apply to avoid restarting app before writing the value in sharedPref
    }

    fun getEnvironment(): String? {
        return sharedPreferences.getString(DEVELOPER_ENVIRONMENT, DEFAULT_ENVIRONMENT)
    }

    fun setServerURL(value: String) {
        sharedPreferences.edit().putString(SERVER_URL, value).commit()
    }

    fun getServerURL(): String? {
        return sharedPreferences.getString(SERVER_URL, BuildConfig.SERVER_URL)
    }
}