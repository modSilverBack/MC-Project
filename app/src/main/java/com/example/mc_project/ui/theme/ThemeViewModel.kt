
package com.example.mc_project.ui.theme

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ThemeViewModel @Inject constructor() : ViewModel() {

    // Theme state
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    // Toggle theme function
    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    // Set theme explicitly
    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }
}