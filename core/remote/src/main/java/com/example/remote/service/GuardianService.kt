package com.example.remote.service

import com.example.remote.model.GuardianResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GuardianService {

    @GET("/search")
    suspend fun search(
        @Query("page") page: Int,
        @Query("q") query: String,
        @Query("show-fields")fields: String = "thumbnail,headline,publication_date,body"
        ): Response<GuardianResponse>
}
