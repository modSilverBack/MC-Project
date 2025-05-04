package com.example.mc_project.data.repository

import com.example.mc_project.data.local.dao.CachedArticlePageDao
import com.example.mc_project.data.local.entity.CachedArticlePageEntity
import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.ArticleRepository
import com.example.mc_project.domain.repository.CachedArticlePageRepository
import javax.inject.Inject

class CachedArticlePageRepositoryImpl @Inject constructor(
    private val apiRepo: ArticleRepository,
    private val dao: CachedArticlePageDao
) : CachedArticlePageRepository {

    override suspend fun getCachedArticlePage(pageNumber: Int): List<Article> {
        // 1. Try to get cached articles for the page
        val cachedPage = dao.getCachedPage(pageNumber)

        if (cachedPage != null && cachedPage.articles.isNotEmpty()) {
            return cachedPage.articles
        }

        // 2. If not cached, fetch 10 random articles from the API
        val randomArticles = apiRepo.getRandomArticles(count = 10)

        // 3. Cache them in DB under the given page number
        val cacheEntity = CachedArticlePageEntity(
            pageNumber = pageNumber,
            articles = randomArticles
        )

        dao.insertCachedPage(cacheEntity)

        return randomArticles
    }
    override suspend fun cacheArticlePage(pageNumber: Int, articles: List<Article>) {
        val entity = CachedArticlePageEntity(pageNumber = pageNumber, articles = articles)
        dao.insertCachedPage(entity)
    }
}
