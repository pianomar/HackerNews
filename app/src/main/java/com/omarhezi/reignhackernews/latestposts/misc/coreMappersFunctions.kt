package com.omarhezi.reignhackernews.latestposts.misc

import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.model.models.PostResponse


fun List<PostResponse?>.toPosts() = mapNotNull { it?.toPost() }

private fun PostResponse.toPost() = Post(
    author,
    storyId,
    createdAt,
    title,
    url,
    storyTitle,
    storyUrl
)