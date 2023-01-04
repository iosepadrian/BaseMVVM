package com.zynksoftware.base.common.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zynksoftware.base.AppConfig
import com.zynksoftware.base.developeroptions.DeveloperSessionManager
import com.zynksoftware.base.developeroptions.DeveloperViewModel
import com.zynksoftware.base.developeroptions.LogProvider
import com.zynksoftware.base.network.services.ApiService
import com.zynksoftware.base.network.services.ServiceProvider
import com.zynksoftware.base.network.interceptors.NetworkNotAvailableInterceptor
import com.zynksoftware.base.repository.LoginRepository
import com.zynksoftware.base.ui.common.SharedViewModel
import com.zynksoftware.base.ui.pager.Fragment1ViewModel
import com.zynksoftware.base.ui.pager.PagerDashboardViewModel
import com.zynksoftware.base.usecase.LoginUseCase
import com.zynksoftware.base.utils.StringResourceProvider
import okhttp3.OkHttpClient

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideApiService(moshi: Moshi, okHttpClient: OkHttpClient): ApiService {
        return ServiceProvider(moshi, okHttpClient).createApiService()
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