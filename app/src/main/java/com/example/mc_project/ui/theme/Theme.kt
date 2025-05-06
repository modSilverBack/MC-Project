package com.example.mc_project.ui.theme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme

// Windows Classic Light Theme
private val ClassicLightColorScheme = lightColorScheme(
    primary = Color(0xFFE0E0E0),         // Button face
    secondary = Color(0xFFC0C0C0),       // Window background
    tertiary = Color(0xFFA0A0A0),        // Button shadow
    background = Color(0xFFC0C0C0),      // Main background
    surface = Color(0xFFE0E0E0),         // Surface panels
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

// Windows Classic Dark Theme (retro-inspired)
private val ClassicDarkColorScheme = darkColorScheme(
    primary = Color(0xFF2B2B2B),         // Button face
    secondary = Color(0xFF3C3C3C),       // Window background
    tertiary = Color(0xFF4D4D4D),        // Button shadow
    background = Color(0xFF2A2A2A),      // Main background
    surface = Color(0xFF383838),         // Surface panels
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MCProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ClassicDarkColorScheme else ClassicLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ClassicTypography,
        content = content
    )
}