package com.zynksoftware.base.models

import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("authorities") var authorities: List<String>? = null,
    @SerializedName("name") var name: String? = null
) {

}