package com.example.mc_project.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.mc_project.data.remote.api.WikipediaApiService
import com.example.mc_project.data.repository.ArticleRepositoryImpl
import com.example.mc_project.domain.repository.ArticleRepository
import com.example.mc_project.domain.usecase.GetRandomArticleUseCase

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWikipediaApi(): WikipediaApiService {
        return Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/api/rest_v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikipediaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(api: WikipediaApiService): ArticleRepository {
        return ArticleRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRandomArticleUseCase(
        repository: ArticleRepository
    ): GetRandomArticleUseCase {
        return GetRandomArticleUseCase(repository)
    }
}