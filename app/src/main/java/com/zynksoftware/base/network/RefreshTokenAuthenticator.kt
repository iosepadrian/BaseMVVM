package com.zynksoftware.base.network

import com.zynksoftware.base.common.extensions.createSignedRequest
import com.zynksoftware.base.common.extensions.retryCount
import com.zynksoftware.base.developeroptions.utils.rxbus.RxBus
import com.zynksoftware.base.developeroptions.utils.rxbus.RxBusEvent
import com.zynksoftware.base.developeroptions.usecase.GetTokenUseCase
import com.zynksoftware.base.developeroptions.usecase.LogoutUseCase
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
    @Inject lateinit var logoutUseCase: LogoutUseCase

    override fun authenticate(route: Route?, response: Response): Request? = when {
        response.retryCount > API_MAX_RETRIES -> {
            logoutUseCase.logoutLocal()
            RxBus.publish(RxBusEvent.LogOut(true))
            null
        }
        else -> response.createSignedRequest(getTokenUseCase.getAccessTokenValid())
    }
}