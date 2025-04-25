    package com.example.mc_project.ui.home

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.mc_project.domain.model.Article
    import com.example.mc_project.domain.usecase.CacheArticlePageUseCase
    import com.example.mc_project.domain.usecase.GetCachedArticlePageUseCase
    import com.example.mc_project.domain.usecase.GetRandomArticlesUseCase
    import com.example.mc_project.domain.usecase.SearchArticlesUseCase
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch
    import javax.inject.Inject

    @HiltViewModel
    class HomeViewModel @Inject constructor(
        private val getCachedArticlePageUseCase: GetCachedArticlePageUseCase,
        private val getArticlesUseCase: GetRandomArticlesUseCase,
        private val searchArticlesUseCase: SearchArticlesUseCase,
        private val cacheArticlePageUseCase: CacheArticlePageUseCase
    ) : ViewModel() {
        private val _articles = MutableStateFlow<List<Article>>(emptyList())
        val articles: StateFlow<List<Article>> = _articles

        private val _selectedArticle = MutableStateFlow<Article>(Article("","","","",""))
        val selectedArticle: StateFlow<Article> = _selectedArticle

        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading

        private val _error = MutableStateFlow<String?>(null)
        val error: StateFlow<String?> = _error

        private val _currentPage = MutableStateFlow(1)
        val currentPage: StateFlow<Int> = _currentPage

        private val _searchQuery = MutableStateFlow("")
        val searchQuery: StateFlow<String> = _searchQuery

        private val articlesPerPage = 10
        private var isSearchMode = false

        init {
            fetchArticles()
        }

        private fun fetchArticles() {
            viewModelScope.launch {
                _isLoading.value = true
                _error.value = null
                try {
                    // Try to fetch cached page
                    _articles.value = getCachedArticlePageUseCase.execute(_currentPage.value)

                    // If no cached articles found, fetch from API
                    if (_articles.value.isEmpty()) {
                        val randomArticles = getArticlesUseCase(articlesPerPage)
                        _articles.value = randomArticles
                        // Cache the articles
                        // You can choose to cache it directly here
                        // Assuming you have a method to cache articles by page in your repo
                        cacheArticlePageUseCase.execute(
                            _currentPage.value,
                            randomArticles
                        )
                    }

                    isSearchMode = false
                } catch (e: Exception) {
                    _error.value = "Failed to load articles: ${e.localizedMessage}"
                } finally {
                    _isLoading.value = false
                }
            }
        }

        fun refreshArticles() {
            _currentPage.value = 1
            if (isSearchMode && _searchQuery.value.isNotEmpty()) {
                searchArticles()
            } else {
                fetchArticles()
            }
        }

        fun updateSearchQuery(query: String) {
            _searchQuery.value = query
        }

        fun searchArticles() {
            if (_searchQuery.value.isEmpty()) {
                fetchArticles()
                return
            }

            viewModelScope.launch {
                _isLoading.value = true
                _error.value = null
                try {
                    val result = listOf(searchArticlesUseCase(_searchQuery.value))
                    _articles.value = result
                    isSearchMode = true

                    if (result.isEmpty() && _currentPage.value > 1) {
                        // If we've gone beyond available results, go back to the previous page
                        _currentPage.value -= 1
                        searchArticles()
                    }
                } catch (e: Exception) {
                    _error.value = "Search failed: ${e.localizedMessage}"
                } finally {
                    _isLoading.value = false
                }
            }
        }

        fun nextPage() {
            _currentPage.value += 1
            if (isSearchMode) {
                searchArticles()
            } else {
                fetchArticles()
            }
        }

        fun previousPage() {
            if (_currentPage.value > 1) {
                _currentPage.value -= 1
                if (isSearchMode) {
                    searchArticles()
                } else {
                    fetchArticles()
                }
            }
        }

        fun selectArticle(article: Article) {
            _selectedArticle.value = article
        }

    }