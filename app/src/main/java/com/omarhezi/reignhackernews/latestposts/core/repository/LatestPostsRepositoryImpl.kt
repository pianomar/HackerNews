package com.omarhezi.reignhackernews.latestposts.core.repository

import com.omarhezi.reignhackernews.latestposts.api.HackerNewsAPI
import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.model.models.ResponseResult
import kotlinx.coroutines.flow.Flow

class LatestPostsRepositoryImpl(
    api: HackerNewsAPI
) : LatestPostsRepository {
    override fun getLatestPosts(): Flow<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun refreshLatestPosts(): ResponseResult<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun getMorePosts(): ResponseResult<List<Post>> {
        TODO("Not yet implemented")
    }
}