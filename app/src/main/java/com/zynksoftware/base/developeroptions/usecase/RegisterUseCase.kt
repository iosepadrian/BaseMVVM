package com.zynksoftware.base.developeroptions.usecase

import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.repository.RegisterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend fun register(registerRequestBody: RegisterRequestBody) = flow {
        emit(Resource.Loading())
        val response = registerRepository.register(registerRequestBody)
        emit(response)
    }
}