package com.example.mc_project.domain.usecase

import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.CachedArticlePageRepository
import javax.inject.Inject

class CacheArticlePageUseCase @Inject constructor(
    private val repository: CachedArticlePageRepository
) {
    suspend fun execute(pageNumber: Int, articles: List<Article>) {
        repository.cacheArticlePage(pageNumber, articles)
    }
}