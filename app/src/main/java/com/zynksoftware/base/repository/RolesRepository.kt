package com.zynksoftware.base.repository

import com.zynksoftware.base.models.Role
import com.zynksoftware.base.models.RoleRequest
import com.zynksoftware.base.models.SuccessResponse
import com.zynksoftware.base.network.RemoteServicesHandler
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.services.ApiService
import javax.inject.Inject

class RolesRepository @Inject constructor(
    private val apiService: ApiService,
    private val servicesHandler: RemoteServicesHandler
) {
    suspend fun getRoles(): Resource<List<Role>> =
        servicesHandler.makeTheCallAndHandleResponse(
            serviceCall = {
                apiService.getRoles()
            },
            mapSuccess = { Resource.Success(it.body(), it.code()) }
        )

    suspend fun editRole(roleRequest: RoleRequest): Resource<SuccessResponse> =
        servicesHandler.makeTheCallAndHandleResponse(
            serviceCall = {
                apiService.editRole(roleRequest)
            },
            mapSuccess = { Resource.Success(it.body(), it.code()) }
        )
}