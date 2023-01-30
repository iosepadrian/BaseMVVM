package com.zynksoftware.base

import com.zynksoftware.base.developeroptions.DeveloperSessionManager
import javax.inject.Inject

class BuildConfigUtils @Inject constructor(private val developerSessionManager: DeveloperSessionManager) {

    companion object {
        private const val DEFAULT_ENVIRONMENT = "Default"
    }

    fun getServerURL(): String? {
        return if (shouldShowDeveloperOption()) {
            if (developerSessionManager.getEnvironment() == DEFAULT_ENVIRONMENT) {
                BuildConfig.SERVER_URL
            } else {
                developerSessionManager.getServerURL()
            }
        }
        else BuildConfig.SERVER_URL
    }

    fun shouldShowDeveloperOption(): Boolean {
        return BuildConfig.SHOULD_SHOW_DEV_SCREEN
    }

}