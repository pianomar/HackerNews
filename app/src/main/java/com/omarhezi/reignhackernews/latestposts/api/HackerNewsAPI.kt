package com.omarhezi.reignhackernews.latestposts.api

import retrofit2.http.GET
import retrofit2.http.QueryMap

//https://hn.algolia.com/api/v1/

interface HackerNewsAPI {
    @GET("search_by_date?query=android")
    suspend fun getLatestPosts(@QueryMap options: Map<String, String?>? = null)
}