package com.omarhezi.reignhackernews

import android.app.Application
import com.omarhezi.reignhackernews.latestposts.di.networkModule
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
                networkModule
            )
        }
    }
}