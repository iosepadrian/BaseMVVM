package com.zynksoftware.base.developeroptions.usecase

import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.network.common.TokenManager
import com.zynksoftware.base.repository.LogoutRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val logoutRepository: LogoutRepository,
    private val tokenManager: TokenManager
) {
    suspend fun logout() = flow {
        emit(Resource.Loading())
        val response = logoutRepository.logout()
        emit(response)
    }

    fun logoutLocal() {
        tokenManager.logout()
    }
}