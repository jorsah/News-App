package com.example.newsapp.core.domain.usecases

import com.example.newsapp.core.domain.repository.NewsRepository
import com.example.newsapp.core.utils.formatDateString
import com.example.remote.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(private val repo: NewsRepository) {
    suspend operator fun invoke(page: Int = 1, query: String = "Armenia"): List<Article> =
        withContext(Dispatchers.IO){
            repo.searchArticles(page, query).map {
                it.copy(webPublicationDate = it.webPublicationDate.formatDateString())
            }
        }
}
