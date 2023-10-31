package com.zynksoftware.base.ui.login

import androidx.lifecycle.MutableLiveData
import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.developeroptions.usecase.LoginUseCase
import com.zynksoftware.base.utils.ConsumableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val loggedInLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val loginResponseLiveData: ConsumableLiveData<String> by lazy {
        ConsumableLiveData(true)
    }

    fun login(loginRequestBody: LoginRequestBody) {
        launchAsync({ loginUseCase.login(loginRequestBody) }, onSuccess = {
            loginResponseLiveData.setValue(it.toString())
            loggedInLiveData.value = true
        })
    }
}