package com.example.mc_project.ui.home

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



