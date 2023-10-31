package com.zynksoftware.base.developeroptions.usecase

import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.repository.ForgotPasswordRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val forgotPasswordRepository: ForgotPasswordRepository
) {
    suspend fun forgotPassword(email: String) = flow {
        emit(Resource.Loading())
        val response = forgotPasswordRepository.forgotPassword(email)
        emit(response)
    }
}