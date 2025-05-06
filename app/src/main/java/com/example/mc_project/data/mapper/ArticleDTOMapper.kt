package com.example.mc_project.data.mapper

import com.example.mc_project.data.remote.model.ArticleDTO.ArticleDTO
import com.example.mc_project.domain.model.Article

fun ArticleDTO.toDomain(): Article {
    return Article(
        title = title,
        description = description,
        extract = extract,
        imageUrl = thumbnail.source,
        sourceUrl = content_urls.mobile.page
    )
}