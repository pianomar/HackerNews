package com.omarhezi.reignhackernews.latestposts.core.models

data class Post(
	val author: String? = null,
	val storyId: Int? = null,
	val createdAt: String? = null,
	val title: String? = null,
	val url: String? = null,
	val storyTitle: String? = null,
	val storyUrl: String? = null
)