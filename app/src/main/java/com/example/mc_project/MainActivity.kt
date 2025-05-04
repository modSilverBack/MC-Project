package com.example.mc_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mc_project.ui.AppThemeWrapper
import com.example.mc_project.ui.home.HomeScreen
import com.example.mc_project.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: HomeViewModel = hiltViewModel()

            AppThemeWrapper(viewModel = viewModel) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                viewModel = viewModel,
                                onArticleClick = { article ->
                                    // Handle article click navigation
                                    viewModel.selectArticle(article)
                                    navController.navigate("article_detail")
                                }
                            )
                        }

                        composable("article_detail") {
                            // Your article detail screen
                            // ArticleDetailScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}