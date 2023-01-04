package com.zynksoftware.base.ui.common

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.common.Status
import com.zynksoftware.base.utils.ConsumableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

    inline fun <T> launchAsync(
        crossinline execute: suspend () -> Flow<Resource<T>>,
        crossinline onSuccess: (T) -> Unit,
        showProgress: Boolean = true
    ) {
        viewModelScope.launch {
            execute().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        setIsLoading(false)
                        onSuccess.invoke(it.data!!)
                    }
                    Status.ERROR -> {
                        setErrorMessage(it.message)
                        setIsLoading(false)
                    }
                    Status.LOADING -> {
                        if (showProgress) {
                            setIsLoading(true)
                        }
                    }
                }
            }
        }
    }
}