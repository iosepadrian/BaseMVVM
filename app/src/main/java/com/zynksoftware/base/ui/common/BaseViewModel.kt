package com.zynksoftware.base.ui.common

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.zynksoftware.base.utils.ConsumableLiveData

open class BaseViewModel: ViewModel(), LifecycleObserver {

    val isLoading = ConsumableLiveData<Boolean>(true)

    var errorMessage = ConsumableLiveData<String>(true)

    fun setIsLoading(value: Boolean) {
        isLoading.setValue(value)
    }

    fun setErrorMessage(message: String?) {
        message?.let {
            if (it.isNotEmpty()) {
                errorMessage.setValue(it)
            }
        }
    }
}