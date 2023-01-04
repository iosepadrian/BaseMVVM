package com.zynksoftware.base.extensions

import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

fun TextView.makeLinks(textSize: Float = getTextSize(), color: Int = currentTextColor, isUnderlined: Boolean = true, vararg links: Pair<String, () -> Unit>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        if(this.text.contains(link.first)) {
            val startIndexesOfLink = this.text.toString().indexesOf(link.first)
            startIndexesOfLink.forEach { startIndexOfLink ->
                val clickableSpan = object : ClickableSpan() {
                    override fun updateDrawState(textPaint: TextPaint) {
                        textPaint.color = color
                        textPaint.isUnderlineText = isUnderlined
                    }

                    override fun onClick(view: View) {
                        Selection.setSelection((view as TextView).text as Spannable, 0)
                        view.invalidate()
                        link.second.invoke()
                    }
                }
                spannableString.setSpan(
                    clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    AbsoluteSizeSpan(textSize.toInt()),
                    startIndexOfLink,
                    startIndexOfLink + link.first.length,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
    val list = mutableListOf<Int>()
    if (this == null || substr.isBlank()) return list

    var i = -1
    while(true) {
        i = indexOf(substr, i + 1, ignoreCase)
        when (i) {
            -1 -> return list
            else -> list.add(i)
        }
    }
}