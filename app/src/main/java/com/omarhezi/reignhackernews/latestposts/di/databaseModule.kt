package com.omarhezi.reignhackernews.latestposts.di

import android.app.Application
import androidx.room.Room
import com.omarhezi.reignhackernews.latestposts.model.database.HackerNewsDatabase
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application) =
        Room
            .databaseBuilder(
                application,
                HackerNewsDatabase::class.java,
                HackerNewsDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()

    fun provideTopPostsDao(database: HackerNewsDatabase) = database.topPostsDao

    single { provideTopPostsDao(provideDatabase(get())) }
}