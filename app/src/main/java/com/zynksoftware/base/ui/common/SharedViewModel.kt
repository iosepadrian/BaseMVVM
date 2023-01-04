package com.zynksoftware.base.ui.common

import androidx.lifecycle.ViewModel
import com.zynksoftware.base.utils.ConsumableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {

    var onNetworkChangedLiveData = ConsumableLiveData<Boolean>(false)
}