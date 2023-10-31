package com.zynksoftware.base.developeroptions.usecase

import com.zynksoftware.base.network.common.TokenManager
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val tokenManager: TokenManager) {

    fun getAccessTokenValid(): String {
        return if (tokenManager.isAccessTokenExpired()) {
            return tokenManager.fetchNewTokenCall() ?: getAccessTokenFromCache()
        } else {
            getAccessTokenFromCache()
        }
    }

    fun getAccessTokenFromCache(): String {
        return tokenManager.getAccessToken()
    }
}