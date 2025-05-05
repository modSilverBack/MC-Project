package com.example.mc_project.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mc_project.domain.model.Article


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailArticleScreen(
    article: Article,
    onBackClick: () -> Unit,
    viewModel: DetailArticleViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isPlaying = viewModel.isPlaying
    val speechRate = viewModel.speechRate

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(article.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier.padding(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Speed Down
                FloatingActionButton(
                    onClick = { viewModel.decreaseSpeed() },
                    modifier = Modifier.size(40.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text("-", style = MaterialTheme.typography.headlineSmall)
                }

                // Speed Up
                FloatingActionButton(
                    onClick = { viewModel.increaseSpeed() },
                    modifier = Modifier.size(40.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text("+", style = MaterialTheme.typography.headlineSmall)
                }

                // Play/Stop Button
                FloatingActionButton(
                    onClick = {
                        article.extract?.let {
                            viewModel.toggleSpeech(it)
                        }
                    }
                ) {
                    Text(if (isPlaying) "Stop" else "Play")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            article.imageUrl?.let { url ->
                AsyncImage(
                    model = ImageRequest.Builder(context).data(url).crossfade(true).build(),
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)
                )
            }

            Text(article.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))

            article.extract?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(16.dp))

            // Show current speed
            Text("Speed: ${"%.2f".format(speechRate)}x", style = MaterialTheme.typography.labelLarge)

            Spacer(Modifier.height(16.dp))

            article.sourceUrl?.let { url ->
                Text(
                    text = "Source: $url",
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}
