package com.zynksoftware.base.network.interceptors

import com.zynksoftware.base.network.NetworkNotAvailableException
import com.zynksoftware.base.utils.network.NetworkConnection
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

class NetworkNotAvailableInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (NetworkConnection.isConnected) {
            chain.proceed(chain.request())
        } else {
            throw NetworkNotAvailableException()
        }
    }
}