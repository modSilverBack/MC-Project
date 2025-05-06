package com.example.mc_project.data.repository

import com.example.mc_project.data.local.dao.BookmarkedArticleDao
import com.example.mc_project.data.mapper.toArticle
import com.example.mc_project.data.mapper.toBookmarkedEntity
import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.BookmarkedArticleRepository
import javax.inject.Inject

class BookmarkedArticleRepositoryImpl @Inject constructor(
    private val dao: BookmarkedArticleDao
) : BookmarkedArticleRepository {

    override suspend fun getBookmarkedArticles(): List<Article> {
        return dao.getAllBookmarks().map { it.toArticle() }
    }

    override suspend fun addBookmark(article: Article) {
        dao.addBookmark(article.toBookmarkedEntity())
    }

    override suspend fun removeBookmark(article: Article) {
        dao.removeBookmark(article.toBookmarkedEntity())
    }

    override suspend fun isBookmarked(article: Article): Boolean {
        return dao.isBookmarked(article.title)
    }
}
