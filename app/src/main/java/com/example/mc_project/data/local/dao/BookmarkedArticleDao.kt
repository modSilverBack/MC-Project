package com.example.mc_project.data.local.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mc_project.data.local.entity.BookmarkedArticleEntity

@Dao
interface BookmarkedArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(article: BookmarkedArticleEntity)

    @Delete
    suspend fun removeBookmark(article: BookmarkedArticleEntity)

    @Query("SELECT * FROM bookmarked_table")
    suspend fun getAllBookmarks(): List<BookmarkedArticleEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_table WHERE title = :title)")
    suspend fun isBookmarked(title: String): Boolean
}
