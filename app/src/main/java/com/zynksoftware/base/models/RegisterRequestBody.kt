package com.zynksoftware.base.models

import com.google.gson.annotations.SerializedName

data class RegisterRequestBody(
    @SerializedName("username") var username: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("clientLocation") var clientLocation: String? = null,
    @SerializedName("isUsing2FA") var isUsing2FA: Boolean? = null
) {

}