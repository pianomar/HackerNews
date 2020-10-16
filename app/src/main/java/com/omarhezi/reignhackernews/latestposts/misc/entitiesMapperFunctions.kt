package com.omarhezi.reignhackernews.latestposts.misc

import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.model.database.entities.PostEntity
import com.omarhezi.reignhackernews.latestposts.model.models.PostResponse

fun List<PostResponse?>.toPostEntities() = mapNotNull { it?.toPostEntity() }

fun PostResponse.toPostEntity() : PostEntity {
    return PostEntity(
        postNumber = PostIndexGenerator.getNextPostIndex(),
        author = author,
        storyId = storyId,
        createdAt = createdAt,
        title = title,
        url = url,
        storyTitle = storyTitle,
        storyUrl = storyUrl
    )
}

fun List<PostEntity>.toPosts() = map { it.toPost() }

fun PostEntity.toPost() = Post(
    author = author,
    storyId = storyId,
    createdAt = createdAt,
    title = title,
    url = url,
    storyTitle = storyTitle,
    storyUrl = storyUrl
)

object PostIndexGenerator {
    var postIndex = 0

    fun getNextPostIndex(): Int {
        postIndex++
        return postIndex
    }
}