package com.zynksoftware.base.utils.accessibility

import android.util.TypedValue
import android.widget.TextView


fun TextView.limitTextSizeTo(scaleLimit: Float = AccessibilityUtils.SCALE_LIMIT_ONE_POINT_FIVE) {
    val sp = textSize / context.resources.displayMetrics.scaledDensity
    if (context.resources.configuration.fontScale > scaleLimit) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleLimit * sp * context.resources.displayMetrics.density)
    }
}

fun TextView?.updateContentDescriptionWith(block: ContentDescriptionFormatter.() -> Unit) {
    if (this == null) return
    this.contentDescription = this.text.toString().getContentDescriptionWith(context, block)
}