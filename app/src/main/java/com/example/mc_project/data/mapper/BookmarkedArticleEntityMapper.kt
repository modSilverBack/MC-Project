package com.example.mc_project.data.mapper

import com.example.mc_project.data.local.entity.BookmarkedArticleEntity
import com.example.mc_project.domain.model.Article

fun Article.toBookmarkedEntity(): BookmarkedArticleEntity {
    return BookmarkedArticleEntity(
        title = title,
        description = description,
        extract = extract,
        imageUrl = imageUrl,
        sourceUrl = sourceUrl
    )
}

fun BookmarkedArticleEntity.toArticle(): Article {
    return Article(
        title = title,
        description = description,
        extract = extract,
        imageUrl = imageUrl,
        sourceUrl = sourceUrl,
        isBookmarked = true
    )
}
