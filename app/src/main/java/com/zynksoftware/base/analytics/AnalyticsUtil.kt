package com.zynksoftware.base.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.utils.accessibility.AccessibilityUtils
import com.zynksoftware.base.utils.accessibility.getFontScale
import com.zynksoftware.base.utils.accessibility.isHighTextContrastEnabled
import com.zynksoftware.base.utils.accessibility.isScreenReaderOn

object AnalyticsUtil {

    private const val LARGE_TEXT_ENABLED_KEY = "LargeTextEnabled"
    private const val LARGE_TEXT_SIZE_KEY = "LargeTextSize"
    private const val VOICE_OVER_ENABLED_KEY = "VoiceOverEnabled"
    private const val HIGH_CONTRAST_ENABLED_KEY = "HighContrastEnabled"

//    TODO, for new projects create these properties on firebase as well
    fun setUserProperties(applicationContext: Context) {
        if (!BuildConfig.DEBUG) {
            FirebaseAnalytics.getInstance(applicationContext).apply {
                setUserProperty(LARGE_TEXT_ENABLED_KEY, if (AccessibilityUtils.isFontScaleLarger(applicationContext)) "true" else "false")
                setUserProperty(LARGE_TEXT_SIZE_KEY, applicationContext.getFontScale().toString())
                setUserProperty(VOICE_OVER_ENABLED_KEY, if (applicationContext.isScreenReaderOn()) "true" else "false")
                setUserProperty(HIGH_CONTRAST_ENABLED_KEY, if (applicationContext.isHighTextContrastEnabled()) "true" else "false")
            }
        }
    }
}