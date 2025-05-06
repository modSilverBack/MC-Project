package com.example.mc_project.domain.usecase

import com.example.mc_project.data.local.dao.BookmarkedArticleDao
import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.BookmarkedArticleRepository
import javax.inject.Inject

class BookmarkArticle @Inject constructor(private val repo: BookmarkedArticleRepository) {
    suspend fun execute(article: Article) {
        repo.addBookmark(article)
    }
}