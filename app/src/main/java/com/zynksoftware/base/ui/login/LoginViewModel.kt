package com.zynksoftware.base.ui.login

import androidx.lifecycle.MutableLiveData
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val loggedInLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun login(email: String, password: String) {
        launchAsync({ loginUseCase.login(email, password) }, onSuccess = {
            loggedInLiveData.value = true
        })
    }
}