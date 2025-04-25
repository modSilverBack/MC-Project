package com.example.mc_project.domain.usecase

import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.ArticleRepository
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(title: String): Article {
        return repository.getArticleByTitle(title)
    }
}
