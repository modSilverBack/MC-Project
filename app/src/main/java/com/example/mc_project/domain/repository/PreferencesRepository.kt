package com.example.mc_project.domain.repository

import com.example.mc_project.domain.model.ReadingPreferences
import com.example.mc_project.domain.model.TextSize
import kotlinx.coroutines.flow.Flow
import androidx.compose.ui.text.font.FontFamily

interface PreferencesRepository {
    val readingPreferencesFlow: Flow<ReadingPreferences>
    suspend fun updateTextSize(textSize: TextSize)
    suspend fun updateFontFamily(fontFamily: FontFamily)
}