package com.example.mc_project.ui.settings

import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mc_project.domain.model.ReadingPreferences
import com.example.mc_project.domain.model.TextSize
import com.example.mc_project.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingPreferencesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val readingPreferences: StateFlow<ReadingPreferences> = preferencesRepository
        .readingPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ReadingPreferences()
        )

    fun updateTextSize(textSize: TextSize) {
        viewModelScope.launch {
            preferencesRepository.updateTextSize(textSize)
        }
    }

    fun updateFontFamily(fontFamily: FontFamily) {
        viewModelScope.launch {
            preferencesRepository.updateFontFamily(fontFamily)
        }
    }
}