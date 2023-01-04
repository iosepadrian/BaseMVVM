package com.zynksoftware.base.ui.factory

import android.content.Context
import com.zynksoftware.base.utils.security.SecurityUtils
import dagger.assisted.AssistedFactory

@AssistedFactory
interface SecurityUtilsFactory {

    fun create(context: Context): SecurityUtils
}