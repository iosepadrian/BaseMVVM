package com.zynksoftware.base.models

import com.google.gson.annotations.SerializedName

data class RoleRequest(
    @SerializedName("roleId") var roleId: String? = null,
    @SerializedName("authorities") var authorities: List<String>? = null
) {

}