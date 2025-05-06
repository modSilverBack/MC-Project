package com.example.mc_project.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mc_project.domain.model.Article
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

// Updated HomeScreen.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit
) {
    val articles by viewModel.articles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isLoading)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Wikipedia Plus",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search articles...") },
                trailingIcon = {
                    IconButton(onClick = { viewModel.searchArticles() }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.searchArticles() })
            )

            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading && articles.isEmpty()) {
                    // Initial loading
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Loading articles...")
                    }
                } else if (articles.isEmpty() && error == null) {
                    // No articles available
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isLoading) "Searching..." else
                                if (searchQuery.isNotEmpty()) "No matching articles found" else
                                    "No articles available",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.refreshArticles() }) {
                            Text("Try Again")
                        }
                    }
                } else {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Swipe-to-refresh and article list
                        SwipeRefresh(
                            state = swipeRefreshState,
                            onRefresh = { viewModel.refreshArticles() },
                            modifier = Modifier.weight(1f)
                        ) {
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = 8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(articles) { article ->
                                    PostCard(article = article, onClick = { onArticleClick(article) })
                                }

                                if (isLoading) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }
                        }

                        // Pagination controls
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { viewModel.previousPage() },
                                enabled = currentPage > 1 && !isLoading
                            ) {
                                Text("Previous")
                            }

                            Text("Page $currentPage")

                            Button(
                                onClick = { viewModel.nextPage() },
                                enabled = !isLoading
                            ) {
                                Text("Next")
                            }
                        }
                    }
                }

                // Show snackbar on error
                error?.let {
                    Snackbar(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        Text(text = it)
                    }
                }
            }
        }
    }
}