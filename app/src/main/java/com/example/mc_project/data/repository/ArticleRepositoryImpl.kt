package com.example.mc_project.data.repository

import com.example.mc_project.data.mapper.toDomain
import com.example.mc_project.data.remote.api.WikipediaApi
import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.ArticleRepository
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

// ArticleRepositoryImpl.kt (updated)
class ArticleRepositoryImpl @Inject constructor(
    private val api: WikipediaApi
) : ArticleRepository {
    override suspend fun getRandomArticle(): Article {
        return api.getRandomArticle().toDomain()
    }

    override suspend fun getArticleByTitle(title: String): Article {
        return api.getArticleByTitle(title).toDomain()
    }
    override suspend fun getMultipleRandomArticles(count: Int): List<Article> {
        // Use coroutines to fetch multiple articles in parallel
        return coroutineScope {
            val articles = mutableListOf<Article>()
            val seenTitles = mutableSetOf<String>()

            // Create multiple async tasks to fetch articles in parallel
            val deferredArticles = List(count * 2) { // Fetch more than needed to account for duplicates
                async {
                    try {
                        api.getRandomArticle().toDomain()
                    } catch (e: Exception) {
                        null
                    }
                }
            }

            // Await all results and filter duplicates
            deferredArticles.awaitAll()
                .filterNotNull()
                .forEach { article ->
                    if (article.title !in seenTitles && articles.size < count) {
                        seenTitles.add(article.title)
                        articles.add(article)
                    }
                }

            articles
        }
    }

}