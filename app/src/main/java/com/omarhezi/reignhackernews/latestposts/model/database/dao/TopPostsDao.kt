package com.omarhezi.reignhackernews.latestposts.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omarhezi.reignhackernews.latestposts.model.database.entities.PostEntity

@Dao
interface TopPostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM ${PostEntity.TABLE_NAME}")
    fun getAllPosts(): List<PostEntity>

    @Query("DELETE FROM ${PostEntity.TABLE_NAME}")
    fun deleteAllPosts()

    @Query("DELETE FROM ${PostEntity.TABLE_NAME} WHERE storyId = :storyId")
    fun deletePost(storyId: Int)
}