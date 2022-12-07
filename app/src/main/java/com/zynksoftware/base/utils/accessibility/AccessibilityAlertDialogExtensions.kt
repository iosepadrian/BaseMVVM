package com.zynksoftware.base.utils.accessibility

import android.app.AlertDialog
import android.widget.TextView

fun androidx.appcompat.app.AlertDialog.updateContentDescriptionSingleChoiceItems(vararg values: String) {
    listView?.post {
        if (values.size == listView.count) {
            for (position in 0 until listView.count) {
                listView.getChildAt(position)?.let { child ->
                    child.findViewById<TextView>(android.R.id.text1)?.contentDescription = values[position]
                }
            }
        }
    }
}

// this method should be used after alertDialog.show() or in onCreateDialog when used in DialogFragment
// if setTitle is used in builder, talkback will say original title, then the content description used here, in that case setTitle should be empty -> setTitle(" ")
// added fallback to set only title in order to show it even if content description may be spoken wrong
fun AlertDialog.updateTitleTextAndContentDescription(title: String, contentDescription: String) {
    val titleId = context.resources.getIdentifier("alertTitle", "id", "android")
    if (titleId > 0) {
        val dialogTitle = findViewById(titleId) as? TextView
        if (dialogTitle != null) {
            dialogTitle.text = title
            dialogTitle.contentDescription = contentDescription
        } else {
            setTitle(title)
        }
    } else {
        setTitle(title)
    }
}

fun androidx.appcompat.app.AlertDialog.updateTitleTextAndContentDescription(title: String, contentDescription: String) {
    val titleId = context.resources.getIdentifier("alertTitle", "id", context.packageName)
    if (titleId > 0) {
        val dialogTitle = findViewById(titleId) as? TextView
        if (dialogTitle != null) {
            dialogTitle.text = title
            dialogTitle.contentDescription = contentDescription
        } else {
            setTitle(title)
        }
    } else {
        setTitle(title)
    }
}

fun androidx.appcompat.app.AlertDialog.updateMessageContentDescription(contentDescription: String) {
    val messageTextView = findViewById(android.R.id.message) as? TextView
    messageTextView?.let {
        it.contentDescription = contentDescription
    }
}