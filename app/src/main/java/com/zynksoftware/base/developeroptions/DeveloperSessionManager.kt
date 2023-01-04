package com.zynksoftware.base.developeroptions

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeveloperSessionManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val LOGS_SWITCH = "logSwitch"
        private const val SCREEN_SWITCH = "screenSwitch"
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
}