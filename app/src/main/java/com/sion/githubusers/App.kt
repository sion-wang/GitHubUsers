package com.sion.githubusers

import android.app.Application
import com.sion.githubusers.di.apiModule
import com.sion.githubusers.widget.log.DebugLogTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugLogTree())
        }

        val module = listOf(
            apiModule
        )

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(module)
        }
    }
}