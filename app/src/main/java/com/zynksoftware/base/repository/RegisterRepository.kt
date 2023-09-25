package com.zynksoftware.base.repository

import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.models.RegisterResponse
import com.zynksoftware.base.network.RemoteServicesHandler
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.common.TokenResponse
import com.zynksoftware.base.network.services.ApiService
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val apiService: ApiService,
    private val servicesHandler: RemoteServicesHandler
) {
    suspend fun register(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse> =
        servicesHandler.makeTheCallAndHandleResponse(
            serviceCall = {
                apiService.register(registerRequestBody)
            },
            mapSuccess = { Resource.Success(it.body(), it.code()) }
        )
}