// GetArticlesListUseCase.kt
package com.example.mc_project.domain.usecase

import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticlesListUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(count: Int = 30): List<Article> {
        return repository.getMultipleRandomArticles(count)
    }
}