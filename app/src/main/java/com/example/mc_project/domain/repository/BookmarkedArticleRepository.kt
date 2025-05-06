package com.example.mc_project.domain.repository

import com.example.mc_project.domain.model.Article

interface BookmarkedArticleRepository {
    suspend fun getBookmarkedArticles(): List<Article>
    suspend fun addBookmark(article: Article)
    suspend fun removeBookmark(article: Article)
    suspend fun isBookmarked(article: Article): Boolean
}