package com.example.mc_project.domain.repository

import com.example.mc_project.domain.model.Article

interface ArticleRepository {
    suspend fun getRandomArticle(): Article
    suspend fun getArticleByTitle(title: String): Article
}
