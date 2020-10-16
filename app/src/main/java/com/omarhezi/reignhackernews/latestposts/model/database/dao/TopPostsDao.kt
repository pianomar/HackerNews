package com.omarhezi.reignhackernews.latestposts.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omarhezi.reignhackernews.latestposts.model.database.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopPostsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM ${PostEntity.TABLE_NAME} ORDER BY postNumber ASC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("DELETE FROM ${PostEntity.TABLE_NAME}")
    suspend fun deleteAllPosts()

    @Query("DELETE FROM ${PostEntity.TABLE_NAME} WHERE storyId = :storyId")
    suspend fun deletePost(storyId: Int)
}