package com.omarhezi.reignhackernews.latestposts.di

import com.omarhezi.reignhackernews.latestposts.core.repository.LatestPostsRepository
import com.omarhezi.reignhackernews.latestposts.core.repository.LatestPostsRepositoryImpl
import com.omarhezi.reignhackernews.latestposts.core.viewmodel.TopPostsViewModel
import com.omarhezi.reignhackernews.latestposts.misc.FormatUtil
import com.omarhezi.reignhackernews.latestposts.misc.StringUtil
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val topPostsViewModel = module {

    single { StringUtil(androidContext()) }

    single { FormatUtil(get()) }

    single<LatestPostsRepository> { LatestPostsRepositoryImpl(get(), get()) }

    viewModel { TopPostsViewModel(get(), get()) }
}