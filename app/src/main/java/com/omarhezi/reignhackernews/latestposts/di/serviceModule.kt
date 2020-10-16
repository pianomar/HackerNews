package com.omarhezi.reignhackernews.latestposts.di

import com.omarhezi.reignhackernews.latestposts.api.HackerNewsAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single<HackerNewsAPI> { provideService(get()) }
}

private inline fun <reified T> provideService(retrofit: Retrofit): T =
    retrofit.create(T::class.java)