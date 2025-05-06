package com.example.mc_project.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mc_project.data.local.converter.ArticleListConverter
import com.example.mc_project.data.local.dao.BookmarkedArticleDao
import com.example.mc_project.data.local.dao.CachedArticlePageDao
import com.example.mc_project.data.local.entity.BookmarkedArticleEntity
import com.example.mc_project.data.local.entity.CachedArticlePageEntity

@Database(
    entities = [CachedArticlePageEntity::class, BookmarkedArticleEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ArticleListConverter::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): CachedArticlePageDao
    abstract fun bookmarkedArticleDao(): BookmarkedArticleDao
}
