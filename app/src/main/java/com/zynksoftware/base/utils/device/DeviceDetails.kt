package com.zynksoftware.base.utils.device

import com.google.gson.annotations.SerializedName

data class DeviceDetails(
    @SerializedName("appVersion") var appVersion: String? = null,
    @SerializedName("deviceBrand") var deviceBrand: String? = null,
    @SerializedName("deviceModel") var deviceModel: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("operatingSystem") var operatingSystem: OperatingSystem? = null,
    @SerializedName("osVersion") var osVersion: String? = null,
    @SerializedName("screenResolution") var screenResolution: String? = null
) {
    enum class OperatingSystem(val value: String) {
        ANDROID("ANDROID"),
        IOS("IOS"),
        WEB("WEB");
    }

}