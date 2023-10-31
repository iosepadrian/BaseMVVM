package com.zynksoftware.base.repository

import com.zynksoftware.base.models.SuccessResponse
import com.zynksoftware.base.network.RemoteServicesHandler
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.services.ApiService
import javax.inject.Inject

class ForgotPasswordRepository @Inject constructor(
    private val apiService: ApiService,
    private val servicesHandler: RemoteServicesHandler
) {
    suspend fun forgotPassword(email: String): Resource<SuccessResponse> =
        servicesHandler.makeTheCallAndHandleResponse(
            serviceCall = {
                apiService.forgotPassword(email)
            },
            mapSuccess = { Resource.Success(it.body(), it.code()) }
        )
}