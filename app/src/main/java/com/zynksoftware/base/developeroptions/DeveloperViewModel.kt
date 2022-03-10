package com.zynksoftware.base.developeroptions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.R
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.utils.StringResourceProvider

class DeveloperViewModel(
    private val stringResourceProvider: StringResourceProvider,
    private val developerSessionManager: DeveloperSessionManager
) : BaseViewModel() {

    fun setLogsSwitch(value: Boolean) {
        developerSessionManager.setLogsSwitch(value)
    }

    fun getLogsSwitch(): Boolean {
        return developerSessionManager.getLogsSwitch()
    }

    fun setScreenSwitch(value: Boolean) {
        developerSessionManager.setScreenSwitch(value)
    }

    fun getScreenSwitch(): Boolean {
        return developerSessionManager.getScreenSwitch()
    }

    fun getAppVersion(): String {
        val versionCode = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME
        return "$versionName ( $versionCode )"
    }

    private fun getDeviceDensityString(densityDpi: Int): String? {
        when (densityDpi) {
            DisplayMetrics.DENSITY_LOW -> return stringResourceProvider.getString(R.string.ldpi)
            DisplayMetrics.DENSITY_MEDIUM -> return stringResourceProvider.getString(R.string.mdpi)
            DisplayMetrics.DENSITY_TV, DisplayMetrics.DENSITY_HIGH -> return stringResourceProvider.getString(
                R.string.hdpi
            )
            DisplayMetrics.DENSITY_260, DisplayMetrics.DENSITY_280, DisplayMetrics.DENSITY_300, DisplayMetrics.DENSITY_XHIGH -> return stringResourceProvider.getString(
                R.string.xhdpi
            )
            DisplayMetrics.DENSITY_340, DisplayMetrics.DENSITY_360, DisplayMetrics.DENSITY_400, DisplayMetrics.DENSITY_420, DisplayMetrics.DENSITY_440, DisplayMetrics.DENSITY_XXHIGH -> return stringResourceProvider.getString(
                R.string.xxhdpi
            )
            DisplayMetrics.DENSITY_560, DisplayMetrics.DENSITY_XXXHIGH -> return stringResourceProvider.getString(
                R.string.xxxhdpi
            )
        }
        return null
    }

    @SuppressLint("HardwareIds")
    fun getSystemDetail(densityDpi: Int): String {
        return stringResourceProvider.getString(R.string.brand) + Build.BRAND.toString() + "\n" +
                stringResourceProvider.getString(R.string.screen_type) + getDeviceDensityString(
            densityDpi
        ) + "\n" +
                stringResourceProvider.getString(R.string.model) + Build.MODEL.toString() + "\n" +
                stringResourceProvider.getString(R.string.id) + Build.ID.toString() + "\n" +
                stringResourceProvider.getString(R.string.sdk) + Build.VERSION.SDK_INT.toString() + "\n" +
                stringResourceProvider.getString(R.string.manufacture) + Build.MANUFACTURER.toString() + "\n" +
                stringResourceProvider.getString(R.string.brand) + Build.BRAND.toString() + "\n" +
                stringResourceProvider.getString(R.string.user) + Build.USER.toString() + "\n" +
                stringResourceProvider.getString(R.string.type) + Build.TYPE.toString() + "\n" +
                stringResourceProvider.getString(R.string.base) + Build.VERSION_CODES.BASE.toString() + "\n" +
                stringResourceProvider.getString(R.string.incremental) + Build.VERSION.INCREMENTAL.toString() + "\n" +
                stringResourceProvider.getString(R.string.board) + Build.BOARD.toString() + "\n" +
                stringResourceProvider.getString(R.string.host) + Build.HOST.toString() + "\n" +
                stringResourceProvider.getString(R.string.fingerprint) + Build.FINGERPRINT.toString() + "\n" +
                stringResourceProvider.getString(R.string.version_code) + Build.VERSION.RELEASE.toString()
    }

    fun sendEmail(context: Context) {
        val email = ""
        val subject = stringResourceProvider.getString(R.string.subject_content)
        val message = stringResourceProvider.getString(R.string.message_content)
        val emailIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putParcelableArrayListExtra(
            Intent.EXTRA_STREAM, DeveloperUtils.getUrisOfLogFiles(context)
        )
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(
            Intent.createChooser(
                emailIntent,
                stringResourceProvider.getString(R.string.sending_email)
            )
        )
    }
}