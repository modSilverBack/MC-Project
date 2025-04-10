package com.example.mc_project.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mc_project.domain.model.Article
<<<<<<< HEAD
import com.example.mc_project.domain.usecase.GetRandomArticleUseCase
=======
<<<<<<< HEAD
import com.example.mc_project.domain.usecase.GetArticlesListUseCase
=======
import com.example.mc_project.domain.usecase.GetRandomArticleUseCase
>>>>>>> upstream/main
>>>>>>> 50cd092 (Made changes)
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
<<<<<<< HEAD
=======
<<<<<<< HEAD
    private val getArticlesListUseCase: GetArticlesListUseCase
) : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val articlesPerPage = 10

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _articles.value = getArticlesListUseCase(articlesPerPage)
            } catch (e: Exception) {
                _error.value = "Failed to load articles: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun refreshArticles() {
        _currentPage.value = 1
        fetchArticles()
    }
=======
>>>>>>> 50cd092 (Made changes)
    private val getRandomArticleUseCase: GetRandomArticleUseCase
) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article

    init {
        fetchRandomArticle()
    }

    fun fetchRandomArticle() {
        viewModelScope.launch {
            _article.value = getRandomArticleUseCase()
        }
    }
<<<<<<< HEAD
=======
>>>>>>> upstream/main
>>>>>>> 50cd092 (Made changes)
}
