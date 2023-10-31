package com.zynksoftware.base.developeroptions.usecase

import com.zynksoftware.base.models.RoleRequest
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.repository.RolesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditRoleUseCase @Inject constructor(
    private val rolesRepository: RolesRepository
) {
    suspend fun editRole(role: RoleRequest) = flow {
        emit(Resource.Loading())
        val response = rolesRepository.editRole(role)
        emit(response)
    }
}