package com.zynksoftware.base

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object AppConfig {

    fun addInterceptors(okHttpClientBuilder: OkHttpClient.Builder) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClientBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request = original.newBuilder().build()
            val response = chain.proceed(request)
            val hasMultipart: Boolean = response.header("Content-Type").equals("application/octet-stream")
            loggingInterceptor.setLevel(if (hasMultipart) HttpLoggingInterceptor.Level.NONE else HttpLoggingInterceptor.Level.BODY)
            return@Interceptor response
        })

        //TODO add flipper
        //okHttpClientBuilder.addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipper))
    }
}