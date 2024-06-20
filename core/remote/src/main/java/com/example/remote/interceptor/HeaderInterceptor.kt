package com.example.remote.interceptor


import com.example.remote.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        requestBuilder
            .addHeader(API_KEY, BuildConfig.API_KEY)
        return chain.proceed(requestBuilder.build())
    }

    private companion object {
        private const val API_KEY = "api-key"
    }
}
