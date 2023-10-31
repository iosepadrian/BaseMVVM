package com.zynksoftware.base.developeroptions.usecase

import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.common.TokenManager
import com.zynksoftware.base.network.common.isSuccessful
import com.zynksoftware.base.repository.LoginRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import android.util.Log
class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenManager: TokenManager
) {
    suspend fun login(loginRequestBody: LoginRequestBody) = flow {
        emit(Resource.Loading())
        val response = loginRepository.login(loginRequestBody)

        if (response.isSuccessful()) {
            tokenManager.saveToken(response.data!!)
        }
        emit(response)
    }
}