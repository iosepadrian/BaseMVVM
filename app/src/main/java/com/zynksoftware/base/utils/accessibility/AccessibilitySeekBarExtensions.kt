package com.zynksoftware.base.utils.accessibility

import android.content.Context
import android.os.Build
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat

fun AppCompatSeekBar.setAccessibilityEventFocused(text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // this will disable percent spoken by talkback
        stateDescription = "\u2800"
    }

    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun sendAccessibilityEvent(host: View, eventType: Int) {
                if (eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    val event = AccessibilityEvent.obtain(eventType)
                    event.eventType = AccessibilityEvent.TYPE_ANNOUNCEMENT
                    event.text.add(text)
                    event.setSource(this@setAccessibilityEventFocused)

                    // send this to be able to adjust seekbar by swipe up/down
                    super.sendAccessibilityEvent(host, AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)

                    // override text spoken with desired one
                    val manager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
                    if (manager.isEnabled) {
                        manager.sendAccessibilityEvent(event)
                    }
                } else {
                    super.sendAccessibilityEvent(host, eventType)
                }
            }
        }
    )
}