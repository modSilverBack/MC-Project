package com.example.mc_project.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mc_project.data.local.converter.ArticleListConverter
import com.example.mc_project.data.local.dao.CachedArticlePageDao
import com.example.mc_project.data.local.entity.CachedArticlePageEntity

@Database(
    entities = [CachedArticlePageEntity::class],
    version = 1
)
@TypeConverters(ArticleListConverter::class)
abstract class CachedArticlePageDatabase : RoomDatabase() {
    abstract fun articleDao(): CachedArticlePageDao
}
