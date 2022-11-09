package com.zynksoftware.base.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zynksoftware.base.developeroptions.DeveloperSessionManager
import com.zynksoftware.base.developeroptions.DeveloperViewModel
import com.zynksoftware.base.developeroptions.LogProvider
import com.zynksoftware.base.developeroptions.recyclerview.PagingViewModel
import com.zynksoftware.base.developeroptions.recyclerview.SimpleRecyclerViewViewModel
import com.zynksoftware.base.network.NetworkExceptionHandler
import com.zynksoftware.base.network.RefreshTokenAuthenticator
import com.zynksoftware.base.network.RemoteServicesHandler
import com.zynksoftware.base.network.interceptors.AuthorizationInterceptor
import com.zynksoftware.base.network.services.ServiceProvider
import com.zynksoftware.base.network.interceptors.NetworkNotAvailableInterceptor
import com.zynksoftware.base.repository.CoinsRepository
import com.zynksoftware.base.ui.common.SharedViewModel
import com.zynksoftware.base.ui.pager.Fragment1ViewModel
import com.zynksoftware.base.ui.pager.PagerDashboardViewModel
import com.zynksoftware.base.usecase.GetCoinsUseCase
import com.zynksoftware.base.utils.StringResourceProvider
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


val viewModelModule = module {
    viewModel { SharedViewModel() }
    viewModel { DeveloperViewModel(get(), get()) }
    viewModel { PagerDashboardViewModel() }
    viewModel { Fragment1ViewModel() }
    viewModel { PagingViewModel() }
    viewModel { SimpleRecyclerViewViewModel(get()) }
}

val repositoryModule = module {

    single { provideSharedPreferences(androidContext()) }
    single { DeveloperSessionManager(get()) }
    single { CoinsRepository(get(), get()) }
//    single { SessionManager(get()) }
//    single { CacheManager(get(), get()) }
}

val utilsModule = module {
    single { NetworkExceptionHandler(get()) }
    single { RemoteServicesHandler(get()) }
    single { StringResourceProvider(get()) }
    single { LogProvider(get()) }
    single { provideMoshi() }
    single { provideOkHttpBuilder() }
    single { ServiceProvider(get(), provideOkHttpBuilder().build()).createApiService() }

//    single { DeviceUtils(get(), androidApplication()) }
//    single { Tracking(get()) }
    single { provideRefreshTokenAuthenticator() }
}

val useCaseModule = module {
    single { GetCoinsUseCase(get()) }
}

fun provideRefreshTokenAuthenticator() = RefreshTokenAuthenticator()

fun provideOkHttpBuilder(): OkHttpClient.Builder {
    val okHttpClientBuilder = OkHttpClient.Builder()

//    AppConfig.addInterceptors(okHttpClientBuilder)

    okHttpClientBuilder
        .addInterceptor(NetworkNotAvailableInterceptor())
//        .addInterceptor(BasicInterceptor())
//        .addInterceptor(VersioningInterceptor())
        .addInterceptor(AuthorizationInterceptor())
        .authenticator(provideRefreshTokenAuthenticator())
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)

    return okHttpClientBuilder
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

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()