package com.example.mc_project.domain.usecase

import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.ArticleRepository
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(query: String, limit: Int, page: Int): List<Article> {
        // Fetch more articles than needed to increase chance of finding matches
        val multiplier = 3
        val articles = repository.getRandomArticles(limit * multiplier)

        // Filter articles by search query (case-insensitive search in title and description)
        val filteredArticles = articles.filter { article ->
            article.title.contains(query, ignoreCase = true) ||
                    article.description?.contains(query, ignoreCase = true) == true
        }

        // Return a paginated subset, or an empty list if no matches
        val startIndex = (page - 1) * limit
        return if (filteredArticles.isEmpty() || startIndex >= filteredArticles.size) {
            emptyList()
        } else {
            val endIndex = minOf(startIndex + limit, filteredArticles.size)
            filteredArticles.subList(startIndex, endIndex)
        }
    }
}