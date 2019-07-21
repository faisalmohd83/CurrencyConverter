package com.fm.exchange

import android.app.Application
import android.os.StrictMode
import com.fm.exchange.di.dataModule
import com.fm.exchange.di.networkModule
import com.fm.exchange.di.utilsModule
import com.fm.exchange.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class ExchangeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setStrictMode()

        injectDependencies()
    }

    /**
     *
     */
    private fun setStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }

    /**
     *
     */
    private fun injectDependencies() {
        startKoin {
            androidLogger()
            androidContext(this@ExchangeApplication)
            modules(
                listOf(
                    viewModule,
                    dataModule,
                    utilsModule,
                    networkModule
                )
            )
        }
    }
}