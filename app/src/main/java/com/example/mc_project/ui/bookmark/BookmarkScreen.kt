package com.example.mc_project.ui.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mc_project.domain.model.Article
import com.example.mc_project.ui.home.PostCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit,
    onSettingsClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val bookmarkedArticles by viewModel.bookmarkedArticles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // If you need to show loading indicator
    val error by viewModel.error.collectAsState() // If you need to show error messages
    val swipeRefreshState = rememberSwipeRefreshState(isLoading)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Bookmarked Articles",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
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
            // If needed, you can add a search bar here like in the HomeScreen

            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading && bookmarkedArticles.isEmpty()) {
                    // Initial loading
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Loading bookmarked articles...")
                    }
                } else if (bookmarkedArticles.isEmpty() && error == null) {
                    // No bookmarks available
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isLoading) "Searching..." else
                                "No bookmarked articles found",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.refreshBookmarkedArticles() }) {
                            Text("Try Again")
                        }
                    }
                } else {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Swipe-to-refresh and bookmarked articles list
                        SwipeRefresh(
                            state = swipeRefreshState,
                            onRefresh = { viewModel.refreshBookmarkedArticles() },
                            modifier = Modifier.weight(1f)
                        ) {
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = 8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(bookmarkedArticles) { article ->
                                    PostCard(article = article, onClick = { onArticleClick(article)})
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
