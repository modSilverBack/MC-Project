package com.example.mc_project.ui.home

<<<<<<< HEAD
=======
<<<<<<< HEAD
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onArticleClick: (String) -> Unit = {}
) {
    val articles by viewModel.articles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()

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
                        text = "No articles available",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.refreshArticles() }) {
                        Text("Try Again")
                    }
                }
            } else {
                // Display the list of articles with page info
                Column {
                    // Articles list
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(articles) { article ->
                            PostCard(
                                article = article,
                                onClick = { onArticleClick(article.title) }
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
            }

            // Refresh button (floating action button)
            FloatingActionButton(
                onClick = { viewModel.refreshArticles() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("Refresh")
            }

            // Show error message if there is one
            error?.let { errorMessage ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(text = errorMessage)
                }
            }
        }
    }
}
=======
>>>>>>> 50cd092 (Made changes)
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val article by viewModel.article.collectAsState()

    if (article == null) {
        // Show a loading indicator while fetching
        Column {
            CircularProgressIndicator()
            Text(text = "Loading article...", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        Column {
            Text(
                text = article!!.title,
                style = MaterialTheme.typography.titleLarge
            )

            article!!.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = article!!.title
                )
            }

            Text(
                text = article!!.extract ?: "No summary available.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}



<<<<<<< HEAD
=======
>>>>>>> upstream/main
>>>>>>> 50cd092 (Made changes)
