package com.example.mc_project.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mc_project.domain.model.Article
import com.example.mc_project.domain.usecase.BookmarkArticle
import com.example.mc_project.domain.usecase.GetBookmarkedArticles
import com.example.mc_project.domain.usecase.RemoveBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkedArticles: GetBookmarkedArticles,
    private val bookmarkArticle: BookmarkArticle,
    private val removeBookmark: RemoveBookmark
) : ViewModel() {

    private val _bookmarkedArticles = MutableStateFlow<List<Article>>(emptyList())
    val bookmarkedArticles: StateFlow<List<Article>> get() = _bookmarkedArticles

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Error state
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        // Fetch the bookmarked articles when the ViewModel is created
        fetchBookmarkedArticles()
    }

    // Fetch bookmarked articles
    private fun fetchBookmarkedArticles() {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                _bookmarkedArticles.value = getBookmarkedArticles.invoke()
            } catch (e: Exception) {
                _error.value = "Failed to load bookmarked articles. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Add an article to the bookmarks
    fun addBookmark(article: Article) {
        viewModelScope.launch {
            try {
                bookmarkArticle.execute(article)
                // Re-fetch the updated list of bookmarked articles
                fetchBookmarkedArticles()
            } catch (e: Exception) {
                _error.value = "Failed to bookmark article. Please try again."
            }
        }
    }

    // Remove an article from the bookmarks
    fun removeBookmark(article: Article) {
        viewModelScope.launch {
            try {
                removeBookmark.execute(article)
                // Re-fetch the updated list of bookmarked articles
                fetchBookmarkedArticles()
            } catch (e: Exception) {
                _error.value = "Failed to remove bookmark. Please try again."
            }
        }
    }

    // Refresh the bookmarked articles (called from UI)
    fun refreshBookmarkedArticles() {
        fetchBookmarkedArticles()
    }
}
