package com.zynksoftware.base.ui.errorhandler

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zynksoftware.base.R

class AlertDialogDisplayer(private val activity: Activity) {

    private var alertDialogBuilder: MaterialAlertDialogBuilder? = null
    private var alertDialog: AlertDialog? = null

    fun showAlertDialog(title: String,
                        message: String,
                        positiveButtonMessage: String?,
                        negativeButtonMessage: String? = null,
                        positiveButtonClickListener: OnPositiveButtonClickListener? = null,
                        negativeButtonClickListener: OnNegativeButtonClickListener? = null) {
        val alertDialogBuilder = MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonMessage) { _, _ ->
                positiveButtonClickListener?.invoke()
            }

        if (negativeButtonMessage != null) {
            alertDialogBuilder.setNegativeButton(negativeButtonMessage) { _, _ ->
                //popup automatically dismiss on button click, no need for listener if button is "cancel"
                negativeButtonClickListener?.invoke()
            }
        }
        alertDialog?.dismiss()
        alertDialog = alertDialogBuilder.create()
        alertDialog?.setCanceledOnTouchOutside(false)
        alertDialog?.show()

        alertDialog?.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(activity.getColor(R.color.red))
        alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(activity.getColor(R.color.black))
    }

    fun showAlertDialog(title: String, message: String) {
        showAlertDialog(title, message, activity.getString(R.string.ok_label))
    }

    fun showError(error: String) {
        showAlertDialog(activity.getString(R.string.error_label), error, activity.getString(R.string.ok_label))
    }

}

internal typealias OnPositiveButtonClickListener = () -> Unit
internal typealias OnNegativeButtonClickListener = () -> Unit