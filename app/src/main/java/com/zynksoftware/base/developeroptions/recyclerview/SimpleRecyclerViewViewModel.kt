package com.zynksoftware.base.developeroptions.recyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zynksoftware.base.models.CryptoModel
import com.zynksoftware.base.network.common.Status
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.usecase.GetCoinsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SimpleRecyclerViewViewModel(
    private val getCoinsUseCase: GetCoinsUseCase
) : BaseViewModel() {

    val listLiveData = MutableLiveData<List<CryptoModel>>()

    fun getList(isFromRefresh: Boolean = false) {
        viewModelScope.launch {
            getCoinsUseCase.getCoins().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        listLiveData.postValue(it.data!!)
                        isLoading.postValue(false)
                    }
                    Status.ERROR -> {
                        errorMessage.postValue(it.message ?: "")
                        isLoading.postValue(false)
                    }
                    Status.LOADING -> {
                        isLoading.postValue(true)
                    }
                }
            }
        }
    }
}