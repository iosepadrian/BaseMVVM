package com.zynksoftware.base.developeroptions.ui.developer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.R
import com.zynksoftware.base.developeroptions.DeveloperSessionManager
import com.zynksoftware.base.developeroptions.utils.DeveloperUtils
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.utils.StringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeveloperViewModel @Inject constructor(
    private val stringResource: StringResource,
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
            DisplayMetrics.DENSITY_LOW -> return stringResource.getString(R.string.ldpi)
            DisplayMetrics.DENSITY_MEDIUM -> return stringResource.getString(R.string.mdpi)
            DisplayMetrics.DENSITY_TV, DisplayMetrics.DENSITY_HIGH -> return stringResource.getString(
                R.string.hdpi
            )
            DisplayMetrics.DENSITY_260, DisplayMetrics.DENSITY_280, DisplayMetrics.DENSITY_300, DisplayMetrics.DENSITY_XHIGH -> return stringResource.getString(
                R.string.xhdpi
            )
            DisplayMetrics.DENSITY_340, DisplayMetrics.DENSITY_360, DisplayMetrics.DENSITY_400, DisplayMetrics.DENSITY_420, DisplayMetrics.DENSITY_440, DisplayMetrics.DENSITY_XXHIGH -> return stringResource.getString(
                R.string.xxhdpi
            )
            DisplayMetrics.DENSITY_560, DisplayMetrics.DENSITY_XXXHIGH -> return stringResource.getString(
                R.string.xxxhdpi
            )
        }
        return null
    }

    @SuppressLint("HardwareIds")
    fun getSystemDetail(densityDpi: Int): String {
        return stringResource.getString(R.string.brand) + Build.BRAND.toString() + "\n" +
                stringResource.getString(R.string.screen_type) + getDeviceDensityString(
            densityDpi
        ) + "\n" +
                stringResource.getString(R.string.model) + Build.MODEL.toString() + "\n" +
                stringResource.getString(R.string.id) + Build.ID.toString() + "\n" +
                stringResource.getString(R.string.sdk) + Build.VERSION.SDK_INT.toString() + "\n" +
                stringResource.getString(R.string.manufacture) + Build.MANUFACTURER.toString() + "\n" +
                stringResource.getString(R.string.brand) + Build.BRAND.toString() + "\n" +
                stringResource.getString(R.string.user) + Build.USER.toString() + "\n" +
                stringResource.getString(R.string.type) + Build.TYPE.toString() + "\n" +
                stringResource.getString(R.string.base) + Build.VERSION_CODES.BASE.toString() + "\n" +
                stringResource.getString(R.string.incremental) + Build.VERSION.INCREMENTAL.toString() + "\n" +
                stringResource.getString(R.string.board) + Build.BOARD.toString() + "\n" +
                stringResource.getString(R.string.host) + Build.HOST.toString() + "\n" +
                stringResource.getString(R.string.fingerprint) + Build.FINGERPRINT.toString() + "\n" +
                stringResource.getString(R.string.version_code) + Build.VERSION.RELEASE.toString()
    }

    fun sendEmail(context: Context) {
        val email = ""
        val subject = stringResource.getString(R.string.subject_content)
        val message = stringResource.getString(R.string.message_content)
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
                stringResource.getString(R.string.sending_email)
            )
        )
    }

    fun logout() {
        //TODO logout user and clear any session of the user before restarting app and change env
    }

    fun getServerURL(): String? {
        return developerSessionManager.getServerURL()
    }

    fun setServerURL(url: String) {
        developerSessionManager.setServerURL(url)
    }

    fun getEnvironment(): String? {
        return developerSessionManager.getEnvironment()
    }

    fun setEnvironment(environment: String) {
        developerSessionManager.setEnvironment(environment)
    }
}