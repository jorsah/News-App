package com.example.localdata.dao

import androidx.room.*
import com.example.localdata.model.ArticleEntity

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(rate: ArticleEntity)

    @Query("DELETE FROM articles WHERE `id` = :id")
    suspend fun deleteArticle(id: String)
}
