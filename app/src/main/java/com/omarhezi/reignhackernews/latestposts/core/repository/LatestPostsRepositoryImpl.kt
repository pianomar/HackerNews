package com.omarhezi.reignhackernews.latestposts.core.repository

import com.omarhezi.reignhackernews.base.BaseRepository
import com.omarhezi.reignhackernews.latestposts.api.HackerNewsAPI
import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.misc.toPostEntities
import com.omarhezi.reignhackernews.latestposts.misc.toPosts
import com.omarhezi.reignhackernews.latestposts.model.database.dao.DeletedPostsDao
import com.omarhezi.reignhackernews.latestposts.model.database.dao.TopPostsDao
import com.omarhezi.reignhackernews.latestposts.model.database.entities.DeletedPostEntity
import com.omarhezi.reignhackernews.latestposts.model.models.PostResponse
import com.omarhezi.reignhackernews.latestposts.model.models.ResponseResult
import kotlinx.coroutines.flow.map

class LatestPostsRepositoryImpl(
    private val api: HackerNewsAPI,
    private val topPostsDao: TopPostsDao,
    private val deletedPostsDao: DeletedPostsDao
) : LatestPostsRepository, BaseRepository(TAG) {

    private var page = 0

    companion object {
        private const val TAG = "LatestPostsRepository"
    }

    override fun getLatestPostsStream() = topPostsDao.getAllPosts().map {
        it.toPosts()
    }

    override suspend fun getLatestPosts() =
        try {
            val posts = getPostsFromAPI()
            insertAllPosts(posts)
            ResponseResult.Success(posts.orEmpty().toPosts())
        } catch (e: Exception) {
            handleResponseError(e)
        }

    private suspend fun insertAllPosts(posts: List<PostResponse?>?) =
        topPostsDao.insertAllPosts(posts.orEmpty().toPostEntities())

    override suspend fun refreshLatestPosts(): ResponseResult<List<Post>> =
        try {
            page = 0 // reset page
            val latestPostsResponse = getPostsFromAPI()
            val posts = latestPostsResponse.orEmpty()

            topPostsDao.deleteAllPosts()
            insertAllPosts(posts)

            ResponseResult.Success(posts.toPosts())
        } catch (e: Exception) {
            handleResponseError(e)
        }

    override suspend fun deletePost(storyId: Int) {
        deletedPostsDao.insertDeletedPost(DeletedPostEntity(storyId = storyId))
        topPostsDao.deletePost(storyId)
    }

    private suspend fun getPostsFromAPI(): List<PostResponse>? {
        val deletedPosts = getDeletedPostsIds()
        val options = getQueryOptions(deletedPosts)
        val latestPostsResponse = api.getLatestPosts(options)
        page = latestPostsResponse.page?.plus(1) ?: 0

        return latestPostsResponse.posts?.filterNotNull()?.filter { post ->
            !deletedPosts.contains(post.storyId)
        }
    }

    private fun getQueryOptions(deletedPosts: List<Int>): MutableMap<String, String> {
        val options = mutableMapOf(Pair("page", page.toString()))
        if (page == 0) options["hitsPerPage"] =
            (20 + deletedPosts.size).toString() //Avoid empty first list
        return options
    }

    private suspend fun getDeletedPostsIds() =
        deletedPostsDao.getAllDeletedPosts().mapNotNull { deletedPost ->
            deletedPost.storyId
        }
}

