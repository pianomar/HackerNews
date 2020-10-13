package com.omarhezi.reignhackernews.latestposts.model.models

import com.squareup.moshi.Json

data class LatestPostsResponse(

	@Json(name = "hits")
    val posts: List<PostResponse?>? = null,

	@Json(name = "page")
    val page: Int? = null
)