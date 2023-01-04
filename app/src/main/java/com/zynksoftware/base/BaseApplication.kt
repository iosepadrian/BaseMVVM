package com.zynksoftware.base

import android.app.Application
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.GsonBuilder
import com.zynksoftware.base.analytics.AnalyticsUtil
import com.zynksoftware.base.common.di.repositoryModule
import com.zynksoftware.base.common.di.useCaseModule
import com.zynksoftware.base.common.di.utilsModule
import com.zynksoftware.base.common.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import st.lowlevel.storo.StoroBuilder

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        initKoin()
        initInterceptor()
        //initCrashlytics()
        AnalyticsUtil.setUserProperties(applicationContext)
        initStoro()
    }

    private fun initKoin() {
//        startKoin {
//            androidContext(this@BaseApplication)
//            androidLogger(level = Level.ERROR)
//            modules(
//                listOf(
//                    repositoryModule,
//                    utilsModule,
//                    useCaseModule
//                )
//            )
//        }
    }

    private fun initInterceptor() {
        //TODO
//        AppConfig.initInterceptor(this)
    }

    private fun initCrashlytics() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    private fun initStoro(){
        val gson = GsonBuilder().create()
        StoroBuilder.configure(CACHE_MANAGER_MAX_STORAGE)
            .setCacheDirectory(filesDir)
            .setGsonInstance(gson)
            .initialize()
    }

    companion object {
        const val CACHE_MANAGER_MAX_STORAGE = 20 * 1024 * 1024L //20MB

        @JvmStatic
        lateinit var appContext: Context

        val applicationScope = GlobalScope
    }
}