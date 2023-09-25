package com.zynksoftware.base.network.common

import com.google.gson.annotations.SerializedName

data class TokenResponse (
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("refresh_token") var refreshToken: String? = null,
    @SerializedName("validityMs") var validityMs: Long? = null,
    @SerializedName("refreshValidityMs") var refreshValidityMs: Long? = null,
)