package com.example.newsapp.core.domain.usecases

import com.example.newsapp.core.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
class GetOfflineArticlesUseCase @Inject constructor(private val repo: NewsRepository) {
    suspend operator fun invoke() = withContext(Dispatchers.IO){
            repo.getOfflineArticles()
        }
}

