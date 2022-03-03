package com.zynksoftware.base.developeroptions

import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.ui.common.BaseViewModel

class DeveloperViewModel : BaseViewModel() {


    fun getAppVersion() :String{
        val versionCode= BuildConfig.VERSION_CODE
        val versionName= BuildConfig.VERSION_NAME
        return "$versionName ( $versionCode )"
    }




}