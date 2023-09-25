package com.zynksoftware.base.utils

import android.app.Activity
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.zynksoftware.base.R

class FirebaseRemoteConfigUtils(private val activity: Activity) {

    companion object {
        private val TAG = FirebaseRemoteConfigUtils::class.simpleName
        private const val MINIMUM_FETCH_INTERVAL_IN_SECONDS: Long = 120

        private const val IN_APP_UPDATE_KEY = "in_app_update_type"
    }

    private val firebaseRemoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = MINIMUM_FETCH_INTERVAL_IN_SECONDS
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    fun fetchInAppUpdateType(onComplete: (InAppUpdateType) -> Unit) {
        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")

                    val inAppUpdateType = firebaseRemoteConfig.getString(IN_APP_UPDATE_KEY)
                    if (inAppUpdateType == InAppUpdateType.FLEXIBLE.value) {
                        onComplete.invoke(InAppUpdateType.FLEXIBLE)
                    } else if (inAppUpdateType == InAppUpdateType.IMMEDIATE.value) {
                        onComplete.invoke(InAppUpdateType.IMMEDIATE)
                    }
                } else {
                    Log.d(TAG, "Config params update failed")
                }
            }
    }
}