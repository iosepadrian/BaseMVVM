package com.zynksoftware.base.usecase

import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.common.TokenManager
import com.zynksoftware.base.network.common.isSuccessful
import com.zynksoftware.base.repository.LoginRepository
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