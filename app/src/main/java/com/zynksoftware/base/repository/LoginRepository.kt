package com.zynksoftware.base.repository

import com.zynksoftware.base.network.RemoteServicesHandler
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.common.TokenResponse
import com.zynksoftware.base.network.services.ApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService,
    private val servicesHandler: RemoteServicesHandler
) {
    suspend fun login(email: String, password: String): Resource<TokenResponse> =
        servicesHandler.makeTheCallAndHandleResponse(
            serviceCall = {
                apiService.login(email, password)
            },
            mapSuccess = { Resource.Success(it.body(), it.code()) }
        )
}