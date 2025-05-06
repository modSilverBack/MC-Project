package com.example.mc_project.data.repository

import android.content.Context
import androidx.compose.ui.text.font.FontFamily
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mc_project.domain.model.ReadingPreferences
import com.example.mc_project.domain.model.TextSize
import com.example.mc_project.domain.repository.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesRepository {

    private object PreferencesKeys {
        val TEXT_SIZE = intPreferencesKey("text_size")
        val FONT_FAMILY = intPreferencesKey("font_family")
    }

    override val readingPreferencesFlow: Flow<ReadingPreferences> = context.dataStore.data.map { preferences ->
        val textSizeOrdinal = preferences[PreferencesKeys.TEXT_SIZE] ?: TextSize.MEDIUM.ordinal
        val fontFamilyOrdinal = preferences[PreferencesKeys.FONT_FAMILY] ?: 0 // Default font

        ReadingPreferences(
            textSize = TextSize.values()[textSizeOrdinal],
            fontFamily = when(fontFamilyOrdinal) {
                0 -> FontFamily.Default
                1 -> FontFamily.Serif
                2 -> FontFamily.SansSerif
                3 -> FontFamily.Monospace
                else -> FontFamily.Default
            }
        )
    }

    override suspend fun updateTextSize(textSize: TextSize) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TEXT_SIZE] = textSize.ordinal
        }
    }

    override suspend fun updateFontFamily(fontFamily: FontFamily) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_FAMILY] = when(fontFamily) {
                FontFamily.Default -> 0
                FontFamily.Serif -> 1
                FontFamily.SansSerif -> 2
                FontFamily.Monospace -> 3
                else -> 0
            }
        }
    }
}