package com.example.mc_project.domain.usecase

import com.example.mc_project.domain.repository.CachedArticlePageRepository
import com.example.mc_project.domain.model.Article
import javax.inject.Inject

class GetCachedArticlePageUseCase @Inject constructor(
    private val cachedArticlePageRepository: CachedArticlePageRepository
) {
    suspend fun execute(pageNumber: Int): List<Article> {
        return cachedArticlePageRepository.getCachedArticlePage(pageNumber)
    }
}