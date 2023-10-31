package com.zynksoftware.base.developeroptions.usecase

import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.repository.RolesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRolesUseCase @Inject constructor(
    private val rolesRepository: RolesRepository
) {
    suspend fun getRoles() = flow {
        emit(Resource.Loading())
        val response = rolesRepository.getRoles()
        emit(response)
    }
}