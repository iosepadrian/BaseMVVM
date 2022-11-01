package com.zynksoftware.base.developeroptions.recyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zynksoftware.base.models.DemoModel
import com.zynksoftware.base.ui.common.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SimpleRecyclerViewViewModel: BaseViewModel() {

    val listLiveData = MutableLiveData<List<DemoModel>>()

    fun getList(isFromRefresh: Boolean = false) {
        viewModelScope.launch {
            delay(2000)
            val items = arrayListOf(
                DemoModel("1", "title 1 $isFromRefresh "),
                DemoModel("2", "title 2"),
                DemoModel("3", "title 3"),
                DemoModel("4", "title 4"),
                DemoModel("5", "title 5"),
                DemoModel("6", "title 6"),
                DemoModel("7", "title 7"),
                DemoModel("8", "title 8"),
                DemoModel("9", "title 9"),
                DemoModel("10", "title 10"),
                DemoModel("11", "title 11"),
                DemoModel("12", "title 12"),
                DemoModel("13", "title 13"),
            )
            listLiveData.postValue(items)
        }
    }
}