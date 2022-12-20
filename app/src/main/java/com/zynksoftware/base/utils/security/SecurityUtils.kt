package com.zynksoftware.base.utils.security

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Debug
import com.google.firebase.crashlytics.internal.common.CommonUtils
import com.nekolaboratory.EmulatorDetector
import com.zynksoftware.base.R
import com.zynksoftware.base.common.extensions.debug
import com.zynksoftware.base.common.extensions.err
import com.zynksoftware.base.ui.errorhandler.AlertDialogDisplayer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class SecurityUtils(val context: Context): KoinComponent {

    private val alertDialogDisplayer: AlertDialogDisplayer by inject { parametersOf(context) }

    fun checkSecurity(): Boolean {
        if (CommonUtils.isRooted(context)) {
            alertDialogDisplayer.showAlertDialog(context.getString(R.string.error_label), context.getString(R.string.device_rooted))
            return false
        }

        if (checkFridaRunningProcess()) {
            alertDialogDisplayer.showAlertDialog(context.getString(R.string.error_label), context.getString(R.string.reverse_engineering_detected))
            return false
        }

        if(checkXposedAndSubstrateApps()) {
            alertDialogDisplayer.showAlertDialog(context.getString(R.string.error_label), context.getString(R.string.reverse_engineering_detected))
            return false
        }

        if (EmulatorDetector.isEmulator(context)) {
            alertDialogDisplayer.showAlertDialog(context.getString(R.string.error_label), context.getString(R.string.emulator_detected))
            return false
        }

        if (isDebuggable(context) || Debug.isDebuggerConnected()) {
            alertDialogDisplayer.showAlertDialog(context.getString(R.string.error_label), context.getString(R.string.device_is_in_debug))
            return false
        }

        return true
    }

    private fun isDebuggable(context: Context): Boolean {
        return context.applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    private fun checkFridaRunningProcess(): Boolean {
        var returnValue = false
        try {
            val process = Runtime.getRuntime().exec("ps")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var read: Int
            val buffer = CharArray(4096)
            val output = StringBuffer()

            do {
                read = reader.read(buffer)
                if(read > 0) {
                    output.append(buffer, 0, read)
                }
            } while (read > 0)

            reader.close()

            process.waitFor()
            if (output.toString().contains("frida-server")) {
                debug("Frida Server process found!")
                returnValue = true
            }
        } catch (e: IOException) {
            err(e)
        } catch (e: InterruptedException) {
            err(e)
        }
        return returnValue
    }

    private fun checkXposedAndSubstrateApps(): Boolean {
        val packageManager = context.packageManager
        val applicationInfoList: List<ApplicationInfo> = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        var found = false
        for (applicationInfo in applicationInfoList) {
            if (applicationInfo.packageName == "de.robv.android.xposed.installer") {
                debug("Xposed found on the system.")
                found = true
                break
            }
            if (applicationInfo.packageName == "com.saurik.substrate") {
                debug("Substrate found on the system.")
                found = true
                break
            }
        }

        return found
    }

}