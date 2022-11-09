package com.zynksoftware.base.network

import com.zynksoftware.base.extensions.createSignedRequest
import com.zynksoftware.base.extensions.retryCount
import com.zynksoftware.base.usecase.GetTokenUseCase
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RefreshTokenAuthenticator: Authenticator, KoinComponent {

    companion object {
        private const val API_MAX_RETRIES = 2
    }

    private val getTokenUseCase: GetTokenUseCase by inject()
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