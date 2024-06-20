package com.example.newsapp.core.domain.repository

import com.example.remote.model.Article

interface NewsRepository {
    suspend fun searchArticles(page: Int, query: String): List<Article>
    suspend fun downloadArticle(article: Article)
    suspend fun getOfflineArticles(): List<Article>
    suspend fun deleteArticle(article: Article)
}