package com.zynksoftware.base.developeroptions.ui.testapi

import androidx.lifecycle.MutableLiveData
import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.models.RoleRequest
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.developeroptions.usecase.EditRoleUseCase
import com.zynksoftware.base.developeroptions.usecase.ForgotPasswordUseCase
import com.zynksoftware.base.developeroptions.usecase.GetRolesUseCase
import com.zynksoftware.base.developeroptions.usecase.LoginUseCase
import com.zynksoftware.base.developeroptions.usecase.LogoutUseCase
import com.zynksoftware.base.developeroptions.usecase.RegisterUseCase
import com.zynksoftware.base.utils.ConsumableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestApiViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val getRolesUseCase: GetRolesUseCase,
    private val editRoleUseCase: EditRoleUseCase,
    private val logoutUseCase: LogoutUseCase
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

    val forgotPasswordSuccessfullyLiveData: ConsumableLiveData<Boolean> by lazy {
        ConsumableLiveData(true)
    }

    val forgotPasswordResponseLiveData: ConsumableLiveData<String> by lazy {
        ConsumableLiveData(true)
    }

    val getRolesSuccessfulyLiveData: ConsumableLiveData<Boolean> by lazy {
        ConsumableLiveData(true)
    }

    val getRolesResponseLiveData: ConsumableLiveData<String> by lazy {
        ConsumableLiveData(true)
    }

    val roleIdLiveData: ConsumableLiveData<String> by lazy {
        ConsumableLiveData(true)
    }

    val editRoleResponseLiveData: ConsumableLiveData<String> by lazy {
        ConsumableLiveData(true)
    }

    val logoutResponseLiveData: ConsumableLiveData<String> by lazy {
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

    fun forgotPassword(email: String) {
        launchAsync({ forgotPasswordUseCase.forgotPassword(email) }, onSuccess = {
            forgotPasswordSuccessfullyLiveData.setValue(true)
            forgotPasswordResponseLiveData.setValue(it.toString())
        })
    }

    fun getRoles() {
        launchAsync({ getRolesUseCase.getRoles() }, onSuccess = {
            getRolesSuccessfulyLiveData.setValue(true)
            getRolesResponseLiveData.setValue(it.toString())
            roleIdLiveData.setValue(it.first().uuid.toString())
        })
    }

    fun editRole(role: RoleRequest) {
        launchAsync({ editRoleUseCase.editRole(role) }, onSuccess = {
            editRoleResponseLiveData.setValue(it.toString())
        })
    }

    fun logout() {
        launchAsync({ logoutUseCase.logout() }, onSuccess = {
            logoutResponseLiveData.setValue(it.toString())
        })
    }
}