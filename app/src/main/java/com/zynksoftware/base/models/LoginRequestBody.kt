package com.zynksoftware.base.models

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("username") var username: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("clientLocation") var clientLocation: String? = null
) {

}