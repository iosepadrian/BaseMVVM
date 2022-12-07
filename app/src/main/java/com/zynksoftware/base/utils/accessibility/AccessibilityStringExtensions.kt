package com.zynksoftware.base.utils.accessibility

import android.content.Context
import com.zynksoftware.base.utils.StringResourceProvider

fun String?.getContentDescriptionWith(context: Context, block: ContentDescriptionFormatter.() -> Unit): String {
    if (this == null) {
        return ""
    }
    val contentDescription = ContentDescriptionFormatter(StringResourceProvider(context), this)
    contentDescription.apply {
        block.invoke(contentDescription)
    }
    return contentDescription.build()
}