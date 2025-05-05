package com.example.mc_project.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mc_project.util.TextToSpeechHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val ttsHelper: TextToSpeechHelper
) : ViewModel() {

    var isPlaying by mutableStateOf(false)
        private set

    var speechRate by mutableStateOf(1.0f)
        private set

    fun toggleSpeech(text: String) {
        if (isPlaying) {
            ttsHelper.stop()
            isPlaying = false
        } else {
            ttsHelper.setSpeechRate(speechRate)
            ttsHelper.speak(text) {
                isPlaying = false
            }
            isPlaying = true
        }
    }

    fun increaseSpeed() {
        updateSpeechRate(speechRate + 0.25f)
    }

    fun decreaseSpeed() {
        updateSpeechRate(speechRate - 0.25f)
    }

    private fun updateSpeechRate(newRate: Float) {
        speechRate = newRate.coerceIn(0.25f, 1.75f)
        ttsHelper.setSpeechRate(speechRate)
    }

    override fun onCleared() {
        super.onCleared()
        ttsHelper.release()
    }
}

