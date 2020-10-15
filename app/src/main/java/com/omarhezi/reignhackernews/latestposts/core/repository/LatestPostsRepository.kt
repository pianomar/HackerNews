package com.omarhezi.reignhackernews.latestposts.core.repository

import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.model.models.ResponseResult
import kotlinx.coroutines.flow.Flow

interface LatestPostsRepository {
    fun getLatestPostsStream(): Flow<List<Post>>

    suspend fun refreshLatestPosts(): ResponseResult<List<Post>>

    suspend fun getLatestPosts(): ResponseResult<Unit>
}