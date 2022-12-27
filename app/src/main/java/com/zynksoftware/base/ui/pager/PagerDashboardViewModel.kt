package com.zynksoftware.base.ui.pager

import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.utils.ConsumableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagerDashboardViewModel @Inject constructor(): BaseViewModel() {

    val tabPositionLiveData = ConsumableLiveData<Int>(false)

    init {
        if (tabPositionLiveData.value == null) {
            tabPositionLiveData.setValue(0)
        }
    }
}