package com.zynksoftware.base.network

import com.zynksoftware.base.common.extensions.createSignedRequest
import com.zynksoftware.base.common.extensions.retryCount
import com.zynksoftware.base.usecase.GetTokenUseCase
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class RefreshTokenAuthenticator: Authenticator {

    companion object {
        private const val API_MAX_RETRIES = 2
    }

    @Inject lateinit var getTokenUseCase: GetTokenUseCase
    //private val logoutUseCase: LogoutUseCase by inject()

    override fun authenticate(route: Route?, response: Response): Request? = when {
        response.retryCount > API_MAX_RETRIES -> {
            //TODO when implement login/logout
            //logoutUseCase.logoutSynchronously()
            null
        }
        else -> response.createSignedRequest(getTokenUseCase.getAccessTokenValid())
    }
}