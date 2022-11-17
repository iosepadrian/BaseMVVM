package com.zynksoftware.base.repository

import com.zynksoftware.base.network.RemoteServicesHandler
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.common.TokenResponse
import com.zynksoftware.base.network.services.ApiService

class LoginRepository(
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