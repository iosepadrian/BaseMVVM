package com.zynksoftware.base.network.common

import com.google.gson.annotations.SerializedName

data class TokenResponse (
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("expires_in") var expiresIn: Float? = null,
    @SerializedName("jti") var jti: String? = null,
    @SerializedName("refresh_token") var refreshToken: String? = null,
    @SerializedName("scope") var scope: String? = null,
    @SerializedName("token_type") var tokenType: String? = null
)