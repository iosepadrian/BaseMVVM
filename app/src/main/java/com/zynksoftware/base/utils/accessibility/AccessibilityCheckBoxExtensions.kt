package com.zynksoftware.base.utils.accessibility

import android.widget.CheckBox

fun CheckBox.adjustSizeBasedOnFontScale() {
    if (AccessibilityUtils.isFontScaleLarger(context)) {
        context.getFontScale().let { fontScale ->
            val checkBoxScale = if (fontScale <= AccessibilityUtils.SCALE_LIMIT_ONE_POINT_EIGHT) {
                fontScale
            } else {
                AccessibilityUtils.SCALE_LIMIT_ONE_POINT_EIGHT
            }
            scaleX = checkBoxScale
            scaleY = checkBoxScale
        }
    }
}