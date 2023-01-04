package com.zynksoftware.base.common.caching

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val USER_ID = "USER_ID"
    }

    fun setUserId(userId: String) {
        sharedPreferences.edit().putString(USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID, "")
    }
}