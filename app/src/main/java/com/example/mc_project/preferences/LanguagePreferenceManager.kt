package com.example.mc_project.preferences

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

object LanguagePreferenceKeys {
    val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
}

class LanguagePreferenceManager(private val context: Context) {
    suspend fun saveLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[LanguagePreferenceKeys.SELECTED_LANGUAGE] = languageCode
        }
    }

    val languageFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LanguagePreferenceKeys.SELECTED_LANGUAGE] ?: "en"
        }
}
