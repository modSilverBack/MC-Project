package com.example.mc_project.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

class TextToSpeechHelper(context: Context) {
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var onCompletionCallback: (() -> Unit)? = null

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val langResult = tts?.setLanguage(Locale.ENGLISH)
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TextToSpeechHelper", "Language not supported or missing data")
                } else {
                    isInitialized = true
                }
            } else {
                Log.e("TextToSpeechHelper", "Initialization failed")
            }
        }
    }

    fun speak(text: String, onCompletion: () -> Unit = {}) {
        stop()
        onCompletionCallback = onCompletion

        if (isInitialized) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            onCompletionCallback?.invoke()
        } else {
            Log.e("TextToSpeechHelper", "TextToSpeech not initialized")
        }
    }

    fun setSpeechRate(rate: Float) {
        if (isInitialized) {
            tts?.setSpeechRate(rate)
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun release() {
        stop()
        tts?.shutdown()
    }
}
