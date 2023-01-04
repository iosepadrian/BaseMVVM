package com.zynksoftware.base.common.extensions

import okhttp3.Request

fun Request.signWithToken(token: String): Request {
    return this.newBuilder()
        .header("Authorization", token)
        .build()
}