package com.zynksoftware.base

import android.app.Application
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.GsonBuilder
import com.zynksoftware.base.di.repositoryModule
import com.zynksoftware.base.di.useCaseModule
import com.zynksoftware.base.di.utilsModule
import com.zynksoftware.base.di.viewModelModule
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import st.lowlevel.storo.StoroBuilder

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        initKoin()
        initInterceptor()
        initCrashlytics()
        initStoro()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger(level = Level.ERROR)
            modules(
                listOf(
                    repositoryModule,
                    utilsModule,
                    viewModelModule,
                    useCaseModule
                )
            )
        }
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