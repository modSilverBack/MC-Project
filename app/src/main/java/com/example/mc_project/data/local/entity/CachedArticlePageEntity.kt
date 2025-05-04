package com.example.mc_project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mc_project.domain.model.Article

@Entity(tableName = "cached_article_page")
data class CachedArticlePageEntity(
    @PrimaryKey val pageNumber: Int,
    val articles: List<Article>,
    )
