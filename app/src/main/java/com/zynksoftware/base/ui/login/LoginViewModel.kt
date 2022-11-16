package com.zynksoftware.base.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zynksoftware.base.network.common.Status
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.usecase.LoginUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val loggedInLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.login(email, password).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        loggedInLiveData.value = true
                        setIsLoading(false)
                    }
                    Status.ERROR -> {
                        setErrorMessage(it.message)
                        setIsLoading(false)
                    }
                    Status.LOADING -> {
                        setIsLoading(true)
                    }
                }
            }
        }
    }
}