package com.zynksoftware.base.developeroptions.recyclerview

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.zynksoftware.base.models.DemoModel
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.ui.common.recyclerview.PagingEvent
import com.zynksoftware.base.ui.common.recyclerview.combineForEvent
import com.zynksoftware.base.ui.common.recyclerview.createPager
import com.zynksoftware.base.utils.ConsumableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(): BaseViewModel() {

    val listLiveData = ConsumableLiveData<PagingData<DemoModel>>(true)

    private val modificationEvents = MutableStateFlow<List<PagingEvent<DemoModel>>>(emptyList())

    fun getDemoList() {
        viewModelScope.launch {
            createPager { page, size ->
                mockInfiniteApi(page, size)
            }
                .flow
                .combineForEvent(viewModelScope, modificationEvents,
                    deleteBlock = { first, second ->
                        return@combineForEvent first.id != second.id
                    },
                    editBlock = { first, second ->
                        return@combineForEvent if (first.id == second.id) {
                            first
                        } else {
                            second
                        }
                    }
                )
                .collect {
                    listLiveData.setValue(it)
                }
        }
    }

    fun delete(item: DemoModel) {
        setIsLoading(true)
        viewModelScope.launch {
            delay(1000) // delete api
            setIsLoading(false)
            modificationEvents.value += PagingEvent.Delete(item)
        }
    }

    fun edit(item: DemoModel, position: Int) {
        setIsLoading(true)
        viewModelScope.launch {
            delay(2000) // delete api
            setIsLoading(false)
            val newItem = item.copy(title = "${item.title} updated")
            modificationEvents.value += PagingEvent.Edit(newItem)
        }
    }


    @Throws(HttpException::class)
    private suspend fun mockInfiniteApi(page: Int, size: Int): MutableList<DemoModel> {
        delay(2000)
        val list = mutableListOf<DemoModel>()
        if (page < 8) {
            if(page == 5) {
                throw (HttpException(Response.error<ResponseBody>(400, "Something went wrong".toResponseBody("plain/text".toMediaType()))).fillInStackTrace())
            }
            for (position in 0..size) {
                list.add(DemoModel("${page}_${position}", "Page $page element: $position"))
            }
        }
        return list
    }
}