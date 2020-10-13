package com.omarhezi.reignhackernews.latestposts.core.models

import com.squareup.moshi.Json

data class Post(

	@Json(name="author")
	val author: String? = null,

	@Json(name="story_id")
	val storyId: Int? = null,

	@Json(name="created_at_i")
	val createdAt: String? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="url")
	val url: String? = null,

	@Json(name="story_title")
	val storyTitle: String? = null,

	@Json(name="story_url")
	val storyUrl: String? = null
)