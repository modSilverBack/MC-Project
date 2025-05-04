package com.example.mc_project.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mc_project.ui.home.HomeViewModel
import com.example.mc_project.ui.theme.MCProjectTheme

@Composable
fun AppThemeWrapper(
    viewModel: HomeViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    MCProjectTheme(
        darkTheme = isDarkTheme,
        dynamicColor = true
    ) {
        content()
    }
}