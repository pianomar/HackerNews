package com.omarhezi.reignhackernews.latestposts.api

import com.omarhezi.reignhackernews.latestposts.model.models.LatestPostsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface HackerNewsAPI {
    @GET("search_by_date?query=android")
    suspend fun getLatestPosts(@QueryMap options: Map<String, String?>? = null) : LatestPostsResponse
}