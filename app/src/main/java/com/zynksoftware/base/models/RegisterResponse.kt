package com.zynksoftware.base.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("successful") var successful: String? = null
) {

}