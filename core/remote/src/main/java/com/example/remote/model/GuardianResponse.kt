package com.example.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GuardianResponse(
    val response: ResponseData
)

data class ResponseData(
    val status: String,
    val currentPage: Int,
    val pages: Int,
    val results: List<Article>
)

@Parcelize
data class Article(
    val id: String,
    val webPublicationDate: String,
    val webTitle: String,
    val fields: Fields?,
) : Parcelable

@Parcelize
data class Fields(
    val headline: String,
    val thumbnail: String,
    val body: String,
): Parcelable
