package com.zynksoftware.base.usecase

import com.zynksoftware.base.network.common.TokenManager

class GetTokenUseCase(private val tokenManager: TokenManager) {

    fun getAccessTokenValid(): String {
        return if(tokenManager.isAccessTokenExpired()) {
            return tokenManager.fetchNewTokenCall() ?: getAccessTokenFromCache()
        } else {
            getAccessTokenFromCache()
        }
    }

    fun getAccessTokenFromCache(): String {
        return tokenManager.getAccessToken()
    }
}