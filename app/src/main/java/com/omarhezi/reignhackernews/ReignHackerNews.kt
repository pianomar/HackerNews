package com.omarhezi.reignhackernews

import android.app.Application
import com.omarhezi.reignhackernews.latestposts.di.databaseModule
import com.omarhezi.reignhackernews.latestposts.di.networkModule
import com.omarhezi.reignhackernews.latestposts.di.serviceModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ReignHackerNews : Application() {
    override fun onCreate() {
        super.onCreate()

        initDI()
    }

    private fun initDI() {
        startKoin {
            androidLogger()
            modules(
                listOf(
                    networkModule,
                    serviceModule,
                    databaseModule
                )
            )
        }
    }
}