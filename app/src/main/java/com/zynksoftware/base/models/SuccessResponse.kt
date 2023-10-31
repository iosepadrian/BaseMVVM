package com.zynksoftware.base.models

import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("successful") var successful: Boolean? = null
) {

}