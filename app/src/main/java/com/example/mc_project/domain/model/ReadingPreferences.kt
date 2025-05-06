package com.example.mc_project.domain.model

import androidx.compose.ui.text.font.FontFamily

data class ReadingPreferences(
    val textSize: TextSize = TextSize.MEDIUM,
    val fontFamily: FontFamily = FontFamily.Default
)

enum class TextSize(val scaleFactor: Float) {
    SMALL(0.8f),
    MEDIUM(1.0f),
    LARGE(1.2f),
    EXTRA_LARGE(1.4f)
}