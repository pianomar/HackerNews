package com.omarhezi.reignhackernews.latestposts.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DeletedPostEntity.TABLE_NAME)
data class DeletedPostEntity(
    @PrimaryKey(autoGenerate = true) val deletedPostId: Int = 0,
    val storyId: Int? = null
) {
    companion object {
        const val TABLE_NAME = "deleted_posts"
    }
}