package com.example.mc_project.domain.model

data class Article(
    val title: String,
    val description: String?,
    val extract: String?,
    val imageUrl: String?,
    val sourceUrl: String?
)