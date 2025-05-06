package com.example.mc_project.data.di

import android.content.Context
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
import com.example.mc_project.preferences.LanguagePreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWikipediaApi(
        @ApplicationContext context: Context,
        languagePreferenceManager: LanguagePreferenceManager
    ): WikipediaApiService {
        val lang = runBlocking { languagePreferenceManager.languageFlow.first() }
        val baseUrl = "https://${lang}.wikipedia.org/api/rest_v1/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
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