package com.zynksoftware.base.utils.accessibility

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager

object AccessibilityUtils {

    const val SCALE_LIMIT_ONE_POINT_EIGHT = 1.8f
    const val SCALE_LIMIT_ONE_POINT_FIVE = 1.5f
    const val SCALE_LIMIT_ONE_POINT_FOUR = 1.4f
    const val SCALE_LIMIT_ONE_POINT_THREE = 1.3f
    const val DEFAULT_FONT_SCALE = 1.0f

    @JvmStatic
    fun getTextSizeLimitPixels(context: Context, textSizePixels: Float, scaleLimit: Float = SCALE_LIMIT_ONE_POINT_FIVE): Float {
        val sp = textSizePixels / context.resources.displayMetrics.scaledDensity
        if (context.getFontScale() > scaleLimit) {
            return scaleLimit * sp * context.resources.displayMetrics.density
        }
        return textSizePixels
    }

    @JvmStatic
    fun getViewSizeLimitPixels(context: Context, defaultSizePixels: Float, scaleLimit: Float = SCALE_LIMIT_ONE_POINT_FIVE): Float {
        val fontScale = context.getFontScale()
        return when {
            fontScale <= DEFAULT_FONT_SCALE -> {
                defaultSizePixels
            }
            fontScale <= scaleLimit -> {
                defaultSizePixels * fontScale
            }
            else -> {
                defaultSizePixels * scaleLimit
            }
        }
    }

    @JvmStatic
    fun isFontScaleLarger(context: Context): Boolean {
        return context.getFontScale() > 1.0f
    }

    @JvmStatic
    fun sendAccessibilityEvent(context: Context, message: String) {
        if (context.isScreenReaderOn()) {
            val manager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            val accessibilityEvent = AccessibilityEvent.obtain()
            accessibilityEvent.eventType = AccessibilityEvent.TYPE_ANNOUNCEMENT
            accessibilityEvent.className = javaClass.name
            accessibilityEvent.packageName = context.packageName
            accessibilityEvent.text.add(message)
            manager.sendAccessibilityEvent(accessibilityEvent)
        }
    }
}

fun Context.isScreenReaderOn(): Boolean {
    val am = getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
    if (am != null && am.isEnabled) {
        val serviceInfoList = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN)
        if (serviceInfoList.isNotEmpty())
            return true
    }
    return false
}

fun Context.isHighTextContrastEnabled(): Boolean {
    return runCatching {
        (getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager)?.let { accessibilityManager ->
            (accessibilityManager.javaClass.getMethod("isHighTextContrastEnabled").invoke(accessibilityManager) as? Boolean)
        }
    }.onFailure { throwable ->
        Log.d(AccessibilityUtils::class.simpleName, "", throwable)
    }.getOrNull() ?: false
}

fun Context.getFontScale(): Float {
    return resources.configuration.fontScale
}
