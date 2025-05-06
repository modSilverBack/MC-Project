package com.example.mc_project.domain.usecase

import com.example.mc_project.domain.repository.CachedArticlePageRepository
import javax.inject.Inject

class ClearCacheUseCase @Inject constructor(private val repo: CachedArticlePageRepository) {
    suspend fun invoke(){
        repo.clearCache()
    }
}