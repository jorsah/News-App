package com.example.newsapp.features.article.navigation

import com.example.navigation.NavigationPayload
import com.example.remote.model.Article
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticlePayload(
    val article: Article,
    override val payloadKey: String = PAYLOAD_KEY
) : NavigationPayload {
    companion object {
        const val PAYLOAD_KEY = "article_key"
    }
}
