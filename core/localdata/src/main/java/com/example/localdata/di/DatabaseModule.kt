package com.example.localdata.di

import android.content.Context
import androidx.room.Room
import com.example.localdata.dao.ArticlesDao
import com.example.localdata.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "articles_database"
    )
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideArticleDao(
        appDatabase: AppDatabase
    ): ArticlesDao = appDatabase.articlesDao()
}
