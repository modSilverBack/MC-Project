package com.example.mc_project.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.usecase.GetRandomArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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
}
