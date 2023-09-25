package com.zynksoftware.base.ui.testapi

import androidx.lifecycle.MutableLiveData
import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.usecase.LoginUseCase
import com.zynksoftware.base.usecase.RegisterUseCase
import com.zynksoftware.base.utils.ConsumableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestApiViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel() {

    val loggedInLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val loginResponseLiveData: ConsumableLiveData<String> by lazy {
        ConsumableLiveData(true)
    }

    val registerSuccessfullyLiveData: ConsumableLiveData<Boolean> by lazy {
        ConsumableLiveData(true)
    }

    val registerResponseLiveData: ConsumableLiveData<String> by lazy {
        ConsumableLiveData(true)
    }

    fun login(loginRequestBody: LoginRequestBody) {
        launchAsync({ loginUseCase.login(loginRequestBody) }, onSuccess = {
            loginResponseLiveData.setValue(it.toString())
            loggedInLiveData.value = true
        })
    }

    fun register(registerRequestBody: RegisterRequestBody) {
        launchAsync({ registerUseCase.register(registerRequestBody) }, onSuccess = {
            registerSuccessfullyLiveData.setValue(true)
            registerResponseLiveData.setValue(it.toString())
        })
    }
}