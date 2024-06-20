package com.example.newsapp.core.domain.usecases

import com.example.newsapp.core.domain.repository.NewsRepository
import com.example.remote.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DownloadArticleUseCase @Inject constructor(private val repo: NewsRepository) {
    suspend operator fun invoke(article: Article) = withContext(Dispatchers.IO) {
        repo.downloadArticle(article)
    }
}
