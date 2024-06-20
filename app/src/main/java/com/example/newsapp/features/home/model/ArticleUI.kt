package com.example.newsapp.features.home.model

import com.example.remote.model.Article
import com.example.remote.model.Fields

data class ArticleUI(
    val id: String,
    val webPublicationDate: String,
    val webTitle: String,
    val fields: Fields?,
    val isDownloaded: Boolean = false
)

fun Article.toUI(isDownloaded: Boolean = false) = ArticleUI(
    id = id,
    webTitle = webTitle,
    webPublicationDate = webPublicationDate,
    fields = fields,
    isDownloaded = isDownloaded
)

fun ArticleUI.toDomain() = Article(
    id = id,
    webTitle = webTitle,
    webPublicationDate = webPublicationDate,
    fields = fields
)
