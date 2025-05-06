package com.example.mc_project.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mc_project.domain.model.Article

@Composable
fun PostCard(
    article: Article,
    onClick: () -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    var isBookmarked by remember { mutableStateOf(article.isBookmarked) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            article.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            val summary = article.description ?: article.extract ?: "No summary available."
            Text(
                text = summary,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    isBookmarked = !isBookmarked
                    if (isBookmarked) {
                        viewModel.addBookmark(article.copy(isBookmarked = true))
                    } else {
                        viewModel.removeBookmark(article.copy(isBookmarked = false))
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isBookmarked) "Remove Bookmark" else "Add Bookmark")
            }
        }
    }
}
