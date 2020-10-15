package com.omarhezi.reignhackernews.latestposts.di

import com.omarhezi.reignhackernews.latestposts.core.repository.LatestPostsRepository
import com.omarhezi.reignhackernews.latestposts.core.repository.LatestPostsRepositoryImpl
import com.omarhezi.reignhackernews.latestposts.core.viewmodel.TopPostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val topPostsViewModel = module {

    single<LatestPostsRepository> { LatestPostsRepositoryImpl(get(), get()) }

    viewModel { TopPostsViewModel(get()) }
}