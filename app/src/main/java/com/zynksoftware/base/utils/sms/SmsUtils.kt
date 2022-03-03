package com.zynksoftware.base.utils.sms

import java.util.regex.Pattern

object SmsUtils {

    private const val SMS_PATTERN_CODE = "\\b\\d{8}\\b"

    fun parseOneTimeCode(message: String?): String {
        if(message == null) {
            return ""
        }
        val matcher = Pattern.compile(SMS_PATTERN_CODE).matcher(message)
        if(matcher.find()) {
            return matcher.group()
        }
        return ""
    }

}