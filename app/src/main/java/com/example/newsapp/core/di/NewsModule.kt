package com.example.newsapp.core.di

import com.example.newsapp.core.data.repository.NewsRepositoryImpl
import com.example.newsapp.core.domain.repository.NewsRepository
import com.example.localdata.dao.ArticlesDao
import com.example.remote.service.GuardianService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NewsModule {
    @Provides
    @Singleton
    fun provideCurrenciesService(
        retrofit: Retrofit
    ): GuardianService = retrofit.create(GuardianService::class.java)

    @Provides
    @Singleton
    fun provideNewsRepo(
        service: GuardianService,
        dao: ArticlesDao
    ): NewsRepository = NewsRepositoryImpl(service, dao)
}