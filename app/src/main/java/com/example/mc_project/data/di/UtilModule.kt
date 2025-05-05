package com.example.mc_project.data.di

import android.app.Application
import com.example.mc_project.util.TextToSpeechHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun provideTextToSpeechHelper(application: Application): TextToSpeechHelper {
        return TextToSpeechHelper(application)
    }
}