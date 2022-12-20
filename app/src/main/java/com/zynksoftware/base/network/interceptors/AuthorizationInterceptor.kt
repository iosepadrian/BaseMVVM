package com.zynksoftware.base.network.interceptors

import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.common.extensions.signWithToken
import com.zynksoftware.base.network.common.AuthorizationType
import com.zynksoftware.base.usecase.GetTokenUseCase
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthorizationInterceptor : Interceptor, KoinComponent {

    private val getTokenUseCase: GetTokenUseCase by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest() = when (AuthorizationType.fromRequest(this)) {
        AuthorizationType.ACCESS_TOKEN -> this.signWithToken(getTokenUseCase.getAccessTokenFromCache())
        AuthorizationType.CLIENT_CREDENTIALS -> this.signWithToken(Credentials.basic(BuildConfig.CREDENTIALS_USERNAME, BuildConfig.CREDENTIALS_PASSWORD))
        AuthorizationType.NONE -> this
    }
}