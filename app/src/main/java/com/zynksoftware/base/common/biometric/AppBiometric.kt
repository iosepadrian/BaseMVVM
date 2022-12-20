package com.zynksoftware.base.common.biometric

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.zynksoftware.base.R
import java.util.concurrent.Executor

class AppBiometric(val activity: AppCompatActivity) {

    companion object {
        private val TAG = AppBiometric::class.simpleName
    }

    private var executor: Executor = ContextCompat.getMainExecutor(activity)
    private var biometricPrompt: BiometricPrompt? = null
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    fun canUseStrongAuthentication(): Boolean {
        return BiometricManager.from(activity)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun init(
        onAuthenticationError: (Int, CharSequence) -> Unit = { _: Int, _: CharSequence -> },
        onAuthenticationSucceeded: () -> Unit = {},
        onAuthenticationFailed: () -> Unit = {}
    ) {
        biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e(TAG, "Biometric authentication error: $errorCode $errString")
                    onAuthenticationError.invoke(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d(TAG, "Biometric authentication succeeded!")
                    onAuthenticationSucceeded.invoke()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.d(TAG, "Biometric authentication failed")
                    onAuthenticationFailed.invoke()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(activity.getString(R.string.biometric_dialog_title))
            .setSubtitle(activity.getString(R.string.biometric_dialog_subtitle))
            .setNegativeButtonText(activity.getString(R.string.biometric_dialog_negative_button))
            .build()
    }

    fun showBiometricDialog() {
        if (biometricPrompt == null) {
            Log.e(TAG, "Must call init before showBiometricDialog")
        } else {
            biometricPrompt!!.authenticate(promptInfo)
        }
    }
}