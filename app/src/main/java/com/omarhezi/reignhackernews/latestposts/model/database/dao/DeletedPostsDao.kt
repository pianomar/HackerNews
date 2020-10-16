package com.omarhezi.reignhackernews.latestposts.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omarhezi.reignhackernews.latestposts.model.database.entities.DeletedPostEntity
import com.omarhezi.reignhackernews.latestposts.model.database.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeletedPostsDao {
    @Query("SELECT * FROM ${DeletedPostEntity.TABLE_NAME}")
    suspend fun getAllDeletedPosts(): List<DeletedPostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedPost(post: DeletedPostEntity)
}