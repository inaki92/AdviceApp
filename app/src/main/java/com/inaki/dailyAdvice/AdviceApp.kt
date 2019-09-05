package com.inaki.dailyAdvice

import android.app.Application
import com.inaki.dailyAdvice.DI.fragmentModule
import com.inaki.dailyAdvice.DI.networkModule
import com.inaki.dailyAdvice.DI.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AdviceApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AdviceApp)
            modules(listOf(networkModule, viewModelModule, fragmentModule))
        }
    }
}