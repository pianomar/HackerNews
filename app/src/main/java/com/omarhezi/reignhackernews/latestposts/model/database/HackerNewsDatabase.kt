package com.omarhezi.reignhackernews.latestposts.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omarhezi.reignhackernews.latestposts.model.database.dao.TopPostsDao
import com.omarhezi.reignhackernews.latestposts.model.database.entities.PostEntity

@Database(
    entities = [
        PostEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HackerNewsDatabase : RoomDatabase() {

    abstract val topPostsDao: TopPostsDao

    companion object {
        const val DATABASE_NAME = "HackerNewsDatabase"
    }
}