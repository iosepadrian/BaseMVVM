package com.zynksoftware.base.common

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.zynksoftware.base.common.caching.SessionManager

class Tracking(private val sessionManager: SessionManager) {

    companion object {
        const val FAILED_TO_ENCRYPT_DATA = "Failed to encrypt data"
        const val FAILED_TO_DECRYPT_DATA = "Failed to decrypt data"

        private const val USER_ID = "userId:"
        const val URL = "url"
        const val HTTP_CODE = "code"
    }

    // use this before logException
    fun log(message: String) {
        FirebaseCrashlytics.getInstance().log(message)
    }

    fun logException(throwable: Throwable){
        setUserIdToReportAsLog()
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    fun logCustomKey(key: String, value: String) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    fun logCustomKey(key: String, value: Int) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    private fun setUserIdToReportAsLog() {
        log("$USER_ID ${sessionManager.getUserId()}")
    }
}