package com.example.localdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localdata.dao.ArticlesDao
import com.example.localdata.model.ArticleEntity

@Database(
    entities = [
        ArticleEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}
