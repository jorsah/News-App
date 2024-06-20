package com.example.newsapp.core.data.mapper

import com.example.localdata.model.ArticleFieldsEntity
import com.example.remote.model.Fields

fun Fields.toDB() = ArticleFieldsEntity(
    headline = headline,
    thumbnail = thumbnail,
    body = body
)

fun ArticleFieldsEntity.toDomain() = Fields(
    headline = headline,
    thumbnail = thumbnail,
    body = body
)
