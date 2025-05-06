package com.example.mc_project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_table")
data class BookmarkedArticleEntity(
    @PrimaryKey val title: String,
    val description: String?,
    val extract: String?,
    val imageUrl: String?,
    val sourceUrl: String?
)
