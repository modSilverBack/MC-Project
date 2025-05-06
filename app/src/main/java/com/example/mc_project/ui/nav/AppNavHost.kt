package com.example.mc_project.ui.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mc_project.ui.home.DetailArticleScreen
import com.example.mc_project.ui.home.HomeScreen
import com.example.mc_project.ui.home.HomeViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = homeViewModel,
                onArticleClick = { article ->
                    homeViewModel.selectArticle(article)
                    navController.navigate("detail")
                }
            )
        }

        composable("detail") {
            val article = homeViewModel.selectedArticle.value
            DetailArticleScreen(
                article = article,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
