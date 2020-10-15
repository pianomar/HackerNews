package com.omarhezi.reignhackernews.latestposts.core.repository

import com.omarhezi.reignhackernews.base.BaseRepository
import com.omarhezi.reignhackernews.latestposts.api.HackerNewsAPI
import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.misc.toPostEntities
import com.omarhezi.reignhackernews.latestposts.misc.toPosts
import com.omarhezi.reignhackernews.latestposts.model.database.dao.TopPostsDao
import com.omarhezi.reignhackernews.latestposts.model.models.PostResponse
import com.omarhezi.reignhackernews.latestposts.model.models.ResponseResult
import kotlinx.coroutines.flow.map

class LatestPostsRepositoryImpl(
    private val api: HackerNewsAPI,
    private val topPostsDao: TopPostsDao
) : LatestPostsRepository, BaseRepository(TAG) {

    companion object {
        private const val TAG = "LatestPostsRepository"
    }

    private var page = 0

    override fun getLatestPostsStream() =
        topPostsDao.getAllPosts().map { it.toPosts() }

    override suspend fun getLatestPosts() =
        try {
            val posts = getPostsFromAPI()
            topPostsDao.insertAllPosts(posts.orEmpty().toPostEntities())
            ResponseResult.Success(Unit)
        } catch (e: Exception) {
            handleResponseError(e)
        }

    override suspend fun refreshLatestPosts(): ResponseResult<List<Post>> =
        try {
            page = 0
            val latestPostsResponse = api.getLatestPosts()
            val posts = latestPostsResponse.posts.orEmpty()

            topPostsDao.deleteAllPosts()
            topPostsDao.insertAllPosts(posts.toPostEntities())

            ResponseResult.Success(posts.toPosts())
        } catch (e: Exception) {
            handleResponseError(e)
        }

    private suspend fun getPostsFromAPI(): List<PostResponse?>? {
        val latestPostsResponse = api.getLatestPosts(mapOf(Pair("page", page.toString())))
        page = latestPostsResponse.page ?: 0
        return latestPostsResponse.posts
    }
}