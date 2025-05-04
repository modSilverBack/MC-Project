package com.example.mc_project.data.di

import android.content.Context
import androidx.room.Room
import com.example.mc_project.data.local.dao.CachedArticlePageDao
import com.example.mc_project.data.local.db.CachedArticlePageDatabase
import com.example.mc_project.data.repository.CachedArticlePageRepositoryImpl
import com.example.mc_project.domain.repository.ArticleRepository
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
    fun provideCachedArticlePageDatabase(@ApplicationContext context: Context): CachedArticlePageDatabase {
        return Room.databaseBuilder(
            context,
            CachedArticlePageDatabase::class.java,
            "wiki_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCachedArticlePageDao(db: CachedArticlePageDatabase): CachedArticlePageDao = db.articleDao()

    @Provides
    @Singleton
    fun provideCachedArticlePageRepository(
        apiRepo: ArticleRepository,
        dao: CachedArticlePageDao
    ): CachedArticlePageRepository {
        return CachedArticlePageRepositoryImpl(apiRepo, dao)
    }
}