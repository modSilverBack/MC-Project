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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onArticleClick : (Article) -> Unit
) {
    val articles by viewModel.articles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // State for SwipeRefresh
    val swipeRefreshState = rememberSwipeRefreshState(isLoading)

    Column(modifier = Modifier.fillMaxSize()) {
        // Title bar at the top using CenterAlignedTopAppBar
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

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Search articles...") },
            trailingIcon = {
                IconButton(onClick = { viewModel.searchArticles() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { viewModel.searchArticles() }
            )
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading && articles.isEmpty()) {
                // Show loading indicator when initially loading
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Loading articles...", style = MaterialTheme.typography.bodyMedium)
                }
            } else if (articles.isEmpty() && error == null) {
                // Show a message when no articles are available
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
                // Display the list of articles with page info and pull-to-refresh
                Column {
                    // SwipeRefresh wrapper around the LazyColumn
                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = { viewModel.refreshArticles() },
                        modifier = Modifier.weight(1f)
                    ) {
                        // Articles list
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(articles) { article ->
                                PostCard(
                                    article = article,
                                    onClick = { onArticleClick(article) }
                                )
                            }

                            // Footer loading indicator
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

                    // Pagination controls - fixed at the bottom, not overlapping with content
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Previous Button
                            Button(
                                onClick = { viewModel.previousPage() },
                                enabled = currentPage > 1 && !isLoading
                            ) {
                                Text("Previous")
                            }

                            // Page indicator
                            Text(
                                text = "Page $currentPage",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            // Next Button
                            Button(
                                onClick = { viewModel.nextPage() },
                                enabled = !isLoading  // Always enabled unless loading
                            ) {
                                Text("Next")
                            }
                        }
                    }
                }
            }

            // Show error message if there is one
            error?.let { errorMessage ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp, 16.dp, 16.dp, 72.dp) // Added bottom padding to avoid overlap with buttons
                ) {
                    Text(text = errorMessage)
                }
            }
        }
    }
}