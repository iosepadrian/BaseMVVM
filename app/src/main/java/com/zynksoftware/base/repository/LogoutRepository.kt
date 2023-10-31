package com.zynksoftware.base.repository

import com.zynksoftware.base.models.SuccessResponse
import com.zynksoftware.base.network.RemoteServicesHandler
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.services.ApiService
import javax.inject.Inject

class LogoutRepository @Inject constructor(
    private val apiService: ApiService,
    private val servicesHandler: RemoteServicesHandler
) {
    suspend fun logout(): Resource<SuccessResponse> =
        servicesHandler.makeTheCallAndHandleResponse(
            serviceCall = {
                apiService.logout()
            },
            mapSuccess = { Resource.Success(it.body(), it.code()) }
        )
}