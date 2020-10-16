package com.omarhezi.reignhackernews.latestposts.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PostEntity.TABLE_NAME)
data class PostEntity(
    val postNumber: Int = 0,
    val author: String? = null,
    @PrimaryKey val storyId: Int? = null,
    val createdAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val storyTitle: String? = null,
    val storyUrl: String? = null
) {
    companion object {
        const val TABLE_NAME = "posts_table"
    }
}