package com.zynksoftware.base.common.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zynksoftware.base.AppConfig
import com.zynksoftware.base.network.RefreshTokenAuthenticator
import com.zynksoftware.base.network.interceptors.AuthorizationInterceptor
import com.zynksoftware.base.network.interceptors.NetworkNotAvailableInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object AppModuleUtils {

    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        AppConfig.addInterceptors(okHttpClientBuilder)

        okHttpClientBuilder
            .addInterceptor(NetworkNotAvailableInterceptor())
//        .addInterceptor(BasicInterceptor())
//        .addInterceptor(VersioningInterceptor())
            .addInterceptor(AuthorizationInterceptor())
            .authenticator(RefreshTokenAuthenticator())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)

        return okHttpClientBuilder.build()
    }

    fun provideSharedPreferences(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "AppPreferences",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}