package com.omarhezi.reignhackernews.latestposts.core.repository

import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.model.models.ResponseResult
import kotlinx.coroutines.flow.Flow

interface LatestPostsRepository {
    fun getLatestPosts() : Flow<List<Post>>

    fun refreshLatestPosts() : ResponseResult<List<Post>>

    fun getMorePosts() : ResponseResult<List<Post>>
}