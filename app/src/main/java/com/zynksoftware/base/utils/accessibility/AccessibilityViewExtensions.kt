package com.zynksoftware.base.utils.accessibility

import android.view.View
import android.view.accessibility.AccessibilityEvent
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.zynksoftware.base.R

fun View.setAccessibilityDoubleTapToDismiss(contentDescription: String) {
    setAccessibilityCustomAction(contentDescription, context.getString(R.string.lbl_double_tap_to_dismiss))
}

fun View.setAccessibilityCustomAction(contentDescription: String, action: String) {
    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK)
                info.isClickable = false
            }
        }
    )
    setContentDescription("$contentDescription. $action")
}

fun View.setAccessibilityDoubleTapAndHoldToMoveUpOrDown() {
    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                // A custom action description. TalkBack will speak "double-tap and hold to move up or down"
                val description: CharSequence = host.context.getString(R.string.lbl_move_up_or_down)
                val customClick = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    AccessibilityNodeInfoCompat.ACTION_LONG_CLICK, description
                )
                sendAccessibilityEvent(host, AccessibilityEvent.TYPE_ANNOUNCEMENT)
                info.addAction(customClick)
            }
        }
    )
}