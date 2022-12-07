package com.zynksoftware.base.utils.accessibility

import com.zynksoftware.base.R
import com.zynksoftware.base.utils.StringResource

class ContentDescriptionFormatter(private val resourcePro: StringResource, private var text: String) {

    fun replaceHyphen(): ContentDescriptionFormatter {
        text = text.replace("-", resourcePro.getString(R.string.lbl_to))
        return this
    }

    fun build(): String = text
}