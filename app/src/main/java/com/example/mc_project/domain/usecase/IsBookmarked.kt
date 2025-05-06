package com.example.mc_project.domain.usecase

import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.repository.BookmarkedArticleRepository
import javax.inject.Inject

class IsBookmarked @Inject constructor(private val repo: BookmarkedArticleRepository){
    suspend fun execute(article: Article): Boolean{
        return repo.isBookmarked(article)
    }
}