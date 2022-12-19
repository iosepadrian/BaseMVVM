package com.zynksoftware.base.usecase

import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.common.TokenManager
import com.zynksoftware.base.network.common.isSuccessful
import com.zynksoftware.base.repository.LoginRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String) = flow {
        emit(Resource.Loading())
        val response = loginRepository.login(email, password)

        if (response.isSuccessful()) {
            tokenManager.saveToken(response.data!!)
        }
        emit(response)
    }
}