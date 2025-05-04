package com.example.mc_project.data.local.converter

import androidx.room.TypeConverter
import com.example.mc_project.domain.model.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArticleListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromArticleList(articles: List<Article>?): String {
        return gson.toJson(articles ?: emptyList<Article>())
    }

    @TypeConverter
    fun toArticleList(data: String?): List<Article> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Article>>() {}.type
        return gson.fromJson(data, listType)
    }
}
