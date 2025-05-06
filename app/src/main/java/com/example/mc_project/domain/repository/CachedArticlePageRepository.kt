package com.example.mc_project.domain.repository

import com.example.mc_project.domain.model.Article

interface CachedArticlePageRepository {
    suspend fun getCachedArticlePage(pageNumber: Int): List<Article>
    suspend fun cacheArticlePage(pageNumber: Int, articles: List<Article>)
    suspend fun clearCache()
}