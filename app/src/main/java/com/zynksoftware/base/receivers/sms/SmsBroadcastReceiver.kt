package com.zynksoftware.base.receivers.sms

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadcastReceiver : BroadcastReceiver() {
    companion object {
        private val TAG = SmsBroadcastReceiver::class.simpleName
    }

    lateinit var smsBroadcastReceiverListener: SmsBroadcastReceiverListener

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val smsRetrieverStatus: Status? = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            if (smsRetrieverStatus?.statusCode == CommonStatusCodes.SUCCESS) {
                val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                try {
                    smsBroadcastReceiverListener.invoke(consentIntent)
                } catch (exception: ActivityNotFoundException) {
                    Log.e(TAG, "", exception)
                }
            }
        }
    }
}
internal typealias SmsBroadcastReceiverListener = (intent: Intent?) -> Unit
