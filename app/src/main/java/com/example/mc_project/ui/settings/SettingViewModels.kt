package com.example.mc_project.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val languagePref: LanguagePreferenceManager
) : ViewModel() {

    val languageCode: StateFlow<String> = languagePref.languageFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = "en"
    )

    fun setLanguage(langCode: String) {
        viewModelScope.launch {
            languagePref.saveLanguage(langCode)
        }
    }
}
