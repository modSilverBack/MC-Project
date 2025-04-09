package com.example.mc_project.data.repository

import com.example.mc_project.data.mapper.toDomain
import com.example.mc_project.data.remote.api.WikipediaApi
import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: WikipediaApi
) : ArticleRepository {
    override suspend fun getRandomArticle(): Article {
        return api.getRandomArticle().toDomain()
    }

    override suspend fun getArticleByTitle(title: String): Article {
        return api.getArticleByTitle(title).toDomain()
    }
}
