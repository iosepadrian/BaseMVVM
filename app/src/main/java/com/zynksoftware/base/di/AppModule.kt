package com.zynksoftware.base.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.zynksoftware.base.ui.common.SharedViewModel
import com.zynksoftware.base.utils.StringResourceProvider
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


val viewModelModule = module {
    viewModel { SharedViewModel() }
}

val repositoryModule = module {

    single { provideSharedPreferences(androidContext()) }
//    single { SessionManager(get()) }
//    single { CacheManager(get(), get()) }
}

val utilsModule = module {
    single { StringResourceProvider(get()) }

//    single { provideMoshi() }
    single { provideOkHttpBuilder() }
//    single { QuantoPayServiceProvider(get(), provideOkHttpBuilder().build()).createApiService() }

//    single { DeviceUtils(get(), androidApplication()) }
//    single { Tracking(get()) }
//    single { provideRefreshTokenAuthenticator() }
}

//fun provideRefreshTokenAuthenticator() = RefreshTokenAuthenticator()

fun provideOkHttpBuilder(): OkHttpClient.Builder {
    val okHttpClientBuilder = OkHttpClient.Builder()

//    AppConfig.addInterceptors(okHttpClientBuilder)

    okHttpClientBuilder
//        .addInterceptor(NetworkNotAvailableInterceptor())
//        .addInterceptor(BasicInterceptor())
//        .addInterceptor(VersioningInterceptor())
//        .addInterceptor(AuthorizationInterceptor())
//        .authenticator(provideRefreshTokenAuthenticator())
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