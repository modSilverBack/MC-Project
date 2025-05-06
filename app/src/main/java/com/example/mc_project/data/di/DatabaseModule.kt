package com.example.mc_project.data.di

import android.content.Context
import androidx.room.Room
import com.example.mc_project.data.local.dao.BookmarkedArticleDao
import com.example.mc_project.data.local.dao.CachedArticlePageDao
import com.example.mc_project.data.local.db.ArticleDatabase
import com.example.mc_project.data.repository.BookmarkedArticleRepositoryImpl
import com.example.mc_project.data.repository.CachedArticlePageRepositoryImpl
import com.example.mc_project.domain.repository.ArticleRepository
import com.example.mc_project.domain.repository.BookmarkedArticleRepository
import com.example.mc_project.domain.repository.CachedArticlePageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
                context,
                ArticleDatabase::class.java,
                "wiki_db"
            ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideCachedArticlePageDao(db: ArticleDatabase): CachedArticlePageDao = db.articleDao()

    @Provides
    @Singleton
    fun provideCachedArticlePageRepository(
        apiRepo: ArticleRepository,
        dao: CachedArticlePageDao
    ): CachedArticlePageRepository {
        return CachedArticlePageRepositoryImpl(apiRepo, dao)
    }

    @Provides
    @Singleton
    fun provideBookmarkedArticleDao(db: ArticleDatabase): BookmarkedArticleDao = db.bookmarkedArticleDao()

    @Provides
    @Singleton
    fun provideBookmarkedArticleRepository(dao: BookmarkedArticleDao): BookmarkedArticleRepository {
        return BookmarkedArticleRepositoryImpl(dao)
    }
}