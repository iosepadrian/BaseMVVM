package com.zynksoftware.base.ui.pager

import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.utils.ConsumableLiveData

class PagerDashboardViewModel: BaseViewModel() {

    val tabPositionLiveData = ConsumableLiveData<Int>(false)

    init {
        if (tabPositionLiveData.value == null) {
            tabPositionLiveData.setValue(1)
        }
    }
}