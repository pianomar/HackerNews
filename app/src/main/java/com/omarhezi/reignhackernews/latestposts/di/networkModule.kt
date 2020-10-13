package com.omarhezi.reignhackernews.latestposts.di

import android.util.Log
import com.omarhezi.reignhackernews.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
}

private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) =
    OkHttpClient.Builder().addInterceptor(interceptor).build()

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("okHttp:", message)
        }
    })

    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return httpLoggingInterceptor
}

private fun provideRetrofit(
    client: OkHttpClient
): Retrofit {
    val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)

    return builder.build()
}