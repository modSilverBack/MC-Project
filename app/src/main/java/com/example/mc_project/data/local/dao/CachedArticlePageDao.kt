package com.example.mc_project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mc_project.data.local.entity.CachedArticlePageEntity

@Dao
interface CachedArticlePageDao {
    @Query("SELECT * FROM cached_article_page WHERE pageNumber = :page")
    suspend fun getCachedPage(page: Int): CachedArticlePageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedPage(cache: CachedArticlePageEntity)

    @Query("DELETE FROM cached_article_page")
    suspend fun clearAllPages()
}
