package com.fm.exchange

import android.app.Application
import com.fm.exchange.di.appLevelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ExchangeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ExchangeApplication)
            modules(appLevelModule)
        }
    }
}