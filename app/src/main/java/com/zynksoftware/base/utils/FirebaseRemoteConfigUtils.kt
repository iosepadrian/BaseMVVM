package com.zynksoftware.base.utils

import android.app.Activity
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.R

class FirebaseRemoteConfigUtils(private val activity: Activity) {

    companion object {
        private val TAG = FirebaseRemoteConfigUtils::class.simpleName
        private const val MINIMUM_FETCH_INTERVAL_IN_SECONDS: Long = 120

        private const val BUILD_NUMBER_KEY = "buildNumberAndroid"
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

                    val buildNumberMandatory = firebaseRemoteConfig.getLong(BUILD_NUMBER_KEY).toInt()

                    Log.d(TAG, "Remote config version code is $buildNumberMandatory")
                    // If current version is lower than firebase version perform immediate update if is available.
                    // otherwise check for flexible update if there is some new updates available
                    if (BuildConfig.VERSION_CODE < buildNumberMandatory) {
                        onComplete.invoke(InAppUpdateType.IMMEDIATE)
                    } else if (BuildConfig.VERSION_CODE > buildNumberMandatory){
                        onComplete.invoke(InAppUpdateType.FLEXIBLE)
                    }
                } else {
                    Log.d(TAG, "Config params update failed")
                }
            }
    }
}