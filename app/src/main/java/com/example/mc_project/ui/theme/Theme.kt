package com.example.mc_project.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// --- COLOR SCHEMES ---

// Light Theme – Cold Gray (Medium)
private val ColdGrayLightColorScheme = lightColorScheme(
    primary = ColdGrayMediumPrimary,
    secondary = ColdGrayMediumSecondary,
    background = ColdGrayMediumBackground,
    surface = ColdGrayMediumSurface,
    onPrimary = ColdGrayMediumOnPrimary,
    onSecondary = ColdGrayMediumOnPrimary,
    onBackground = ColdGrayMediumOnBackground,
    onSurface = ColdGrayMediumOnSurface,
    primaryContainer = ColdGrayMediumSecondary,
    onPrimaryContainer = ColdGrayMediumOnPrimary,
)


// --- TYPOGRAPHY ---

private val CustomTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    )
)

// --- THEME WRAPPER FUNCTION ---

// theme.kt
@Composable
fun MCProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // auto-switch by default
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColdGrayLightColorScheme, // Cold Gray theme
        typography = ClassicTypography, // Classic typography
        content = content
    )
}
