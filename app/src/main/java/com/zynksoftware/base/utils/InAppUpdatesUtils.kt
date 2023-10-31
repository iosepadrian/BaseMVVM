package com.zynksoftware.base.utils

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.zynksoftware.base.R
import com.zynksoftware.base.ui.errorhandler.AlertDialogDisplayer

// https://developer.android.com/guide/playcore/in-app-updates/kotlin-java
class InAppUpdatesUtils(private val activity: AppCompatActivity) {

    companion object {
        private val TAG = InAppUpdatesUtils::class.simpleName
        private const val DAYS_FOR_FLEXIBLE_UPDATE = 7

        private const val FLEXIBLE_UPDATE_REQUEST_CODE = 998
        private const val IMMEDIATE_UPDATE_REQUEST_CODE = 997
    }

    private val appUpdateManager = AppUpdateManagerFactory.create(activity)
    private val appUpdateInfoTask = appUpdateManager.appUpdateInfo
    private var updateType: InAppUpdateType = InAppUpdateType.FLEXIBLE

    private val updateFlowResultLauncher =
        activity.registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.d(TAG, "OnActivityResult ok")
            } else {
                Log.e(TAG, "Update flow failed! Result code: ${result.resultCode}")
                if (result.resultCode == RESULT_CANCELED && updateType == InAppUpdateType.IMMEDIATE) {
                    activity.finishAffinity()
                }
            }
        }

    private val starter = IntentSenderForResultStarter { intent, _, fillInIntent, flagsMask, flagsValues, _, _ ->
        val request = IntentSenderRequest.Builder(intent)
            .setFillInIntent(fillInIntent)
            .setFlags(flagsValues, flagsMask)
            .build()

        updateFlowResultLauncher.launch(request)
    }

    private val downloadListener: InstallStateUpdatedListener = InstallStateUpdatedListener { installState ->
        if (installState.installStatus() == InstallStatus.DOWNLOADING) {
            val bytesDownloaded = installState.bytesDownloaded()
            val totalBytesToDownload = installState.totalBytesToDownload()
            // Show update progress bar.
            if (totalBytesToDownload != 0L) {
                val percentage = String.format("%.2f", (bytesDownloaded.toDouble() / totalBytesToDownload) * 100)
                Log.d(TAG, "downloading: ${percentage}%")
            }
        } else if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            Log.d(TAG, "downloaded")
            showDialogForCompleteUpdate()
        }
    }

    fun checkForUpdates() {
        FirebaseRemoteConfigUtils(activity).fetchInAppUpdateType(onComplete = {
            updateType = it
            if (it == InAppUpdateType.FLEXIBLE) {
                checkForFlexibleInAppUpdate()
            } else if (it == InAppUpdateType.IMMEDIATE) {
                checkForImmediateAppUpdate()
            }
        })
    }

    fun onResume() {
        Log.d(TAG, "onResume")
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                when (updateType) {
                    InAppUpdateType.FLEXIBLE -> {
                        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                            Log.d(TAG, "Flexible: status downloaded")
                            showDialogForCompleteUpdate()
                        }
                    }
                    InAppUpdateType.IMMEDIATE -> {
                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            Log.d(TAG, "Immediate: already running, resume the update")
                            // If an in-app update is already running, resume the update.
                            appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                AppUpdateType.IMMEDIATE,
                                starter,
                                IMMEDIATE_UPDATE_REQUEST_CODE
                            )
                        }
                    }
                }
            }
    }

    fun onDestroy() {
        if (updateType == InAppUpdateType.FLEXIBLE) {
            appUpdateManager.unregisterListener(downloadListener)
        }
    }

    private fun showDialogForCompleteUpdate() {
        if (!activity.isFinishing) {
            AlertDialogDisplayer(activity).showAlertDialog(
                title = activity.getString(R.string.lbl_app_update_title),
                message = activity.getString(R.string.lbl_app_update_message),
                positiveButtonMessage = activity.getString(R.string.lbl_restart),
                positiveButtonClickListener = {
                    appUpdateManager.completeUpdate()
                }
            )
        }
    }

    private fun checkForFlexibleInAppUpdate() {
        Log.d(TAG, "checkForFlexibleInAppUpdate")
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
//                && (appUpdateInfo.clientVersionStalenessDays() ?: -1) >= DAYS_FOR_FLEXIBLE_UPDATE
            ) {
                Log.d(TAG, "checkForFlexibleInAppUpdate: Update available. start update flow")
                appUpdateManager.registerListener(downloadListener)
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    starter,
                    FLEXIBLE_UPDATE_REQUEST_CODE
                )
            }
        }.addOnFailureListener {
            Log.i(TAG, "checkForFlexibleInAppUpdate error", it)
        }
    }

    private fun checkForImmediateAppUpdate() {
        Log.d(TAG, "checkForImmediateAppUpdate")
        // Each AppUpdateInfo instance can be used to start an update only once
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                Log.d(TAG, "checkForImmediateAppUpdate: Update available. start update flow")
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    starter,
                    IMMEDIATE_UPDATE_REQUEST_CODE
                )
            }
        }.addOnFailureListener {
            Log.i(TAG, "checkForImmediateAppUpdate error", it)
        }
    }
}