package com.omarhezi.reignhackernews.latestposts.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omarhezi.reignhackernews.latestposts.model.database.dao.DeletedPostsDao
import com.omarhezi.reignhackernews.latestposts.model.database.dao.TopPostsDao
import com.omarhezi.reignhackernews.latestposts.model.database.entities.DeletedPostEntity
import com.omarhezi.reignhackernews.latestposts.model.database.entities.PostEntity

@Database(
    entities = [PostEntity::class, DeletedPostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HackerNewsDatabase : RoomDatabase() {

    abstract val topPostsDao: TopPostsDao
    abstract val deletedPostsDao: DeletedPostsDao

    companion object {
        const val DATABASE_NAME = "HackerNewsDatabase"
    }
}