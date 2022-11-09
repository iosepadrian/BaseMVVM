package com.zynksoftware.base.network.common

import okhttp3.Request

enum class AuthorizationType {
    ACCESS_TOKEN,
    CLIENT_CREDENTIALS,
    NONE;

    companion object {
        fun fromRequest(request: Request): AuthorizationType =
            request.tag(AuthorizationType::class.java) ?: ACCESS_TOKEN
    }
}