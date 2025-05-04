package com.example.mc_project.data.remote.api

import com.example.mc_project.data.remote.model.ArticleDTO.ArticleDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface WikipediaApiService {
    @GET("page/random/summary")
    suspend fun getRandomArticle(): ArticleDTO

    @GET("page/summary/{title}")
    suspend fun getArticleByTitle(
        @Path("title") title: String
    ): ArticleDTO

}