package com.example.newsapp.core.data.mapper

import com.example.localdata.model.ArticleEntity
import com.example.remote.model.Article

fun Article.toDB() =
    ArticleEntity(
        id = id,
        date = webPublicationDate,
        title = webTitle,
        fields = fields?.toDB()
    )

fun ArticleEntity.toDomain() =
    Article(
        id = id,
        webPublicationDate = date,
        webTitle = title,
        fields = fields?.toDomain()
    )
