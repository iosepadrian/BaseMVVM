package com.zynksoftware.base.network.services

import com.squareup.moshi.Moshi
import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.BuildConfigUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import android.util.Log

class ServiceProvider(private val moshi: Moshi, private val okHttpClient: OkHttpClient, private val buildConfigUtils: BuildConfigUtils) {

    fun createApiService(): ApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(buildConfigUtils.getServerURL()!!)
            .addConverterFactory(MoshiConverterFactory.create(moshi))

        val retrofit = retrofitBuilder.client(okHttpClient).build()
        return retrofit.create(ApiService::class.java)
    }

}
