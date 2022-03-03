package com.zynksoftware.base.ui.common

import androidx.lifecycle.ViewModel
import com.zynksoftware.base.utils.ConsumableLiveData

class SharedViewModel: ViewModel() {

    var isLoading = ConsumableLiveData<Boolean>(true)

    var onNetworkChangedLiveData = ConsumableLiveData<Boolean>(false)

    fun setIsLoading(value: Boolean) {
        isLoading.setValue(value)
    }
}