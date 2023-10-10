package com.zynksoftware.base.common.di

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.zynksoftware.base.BuildConfigUtils
import com.zynksoftware.base.developeroptions.utils.LogProvider
import com.zynksoftware.base.network.services.ApiService
import com.zynksoftware.base.network.services.ServiceProvider
import com.zynksoftware.base.utils.StringResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideApiService(moshi: Moshi, okHttpClient: OkHttpClient, buildConfigUtils: BuildConfigUtils): ApiService {
        return ServiceProvider(moshi, okHttpClient, buildConfigUtils).createApiService()
    }

    @Provides
    fun providesOkHttp(): OkHttpClient {
        return AppModuleUtils.provideOkHttpClient()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return AppModuleUtils.provideMoshi()
    }

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return AppModuleUtils.provideSharedPreferences(context)
    }

    @Provides
    fun providesLogProvider(@ApplicationContext context: Context): LogProvider {
        return LogProvider(context)
    }

    @Provides
    fun providesStringResourceProvider(@ApplicationContext context: Context): StringResourceProvider {
        return StringResourceProvider(context)
    }
}