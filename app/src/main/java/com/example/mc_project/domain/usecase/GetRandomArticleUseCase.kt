package com.example.mc_project.domain.usecase


import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.ArticleRepository
import javax.inject.Inject

class GetRandomArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(): Article {
        return repository.getRandomArticle()
    }
}
