package com.example.newsapp.core.data.repository

import com.example.newsapp.core.data.mapper.toDB
import com.example.newsapp.core.data.mapper.toDomain
import com.example.newsapp.core.domain.repository.NewsRepository
import com.example.localdata.dao.ArticlesDao
import com.example.remote.model.Article
import com.example.remote.service.GuardianService
import java.io.IOException

class NewsRepositoryImpl(
    private val service: GuardianService,
    private val dao: ArticlesDao
) : NewsRepository {
    override suspend fun searchArticles(page: Int, query: String): List<Article> {
        val response = service.search(page, query)
        if (!response.isSuccessful) throw IOException(response.message())

        return response.body()?.response?.results ?: emptyList()
    }

    override suspend fun downloadArticle(article: Article) {
        dao.insertArticle(article.toDB())
    }

    override suspend fun getOfflineArticles(): List<Article> =
        dao.getArticles().map { it.toDomain() }


    override suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(article.id)
    }
}

