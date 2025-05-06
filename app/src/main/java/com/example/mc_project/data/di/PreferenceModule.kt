package com.example.mc_project.data.di

import android.content.Context
import com.example.mc_project.preferences.LanguagePreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideLanguagePreferenceManager(
        @ApplicationContext context: Context
    ): LanguagePreferenceManager {
        return LanguagePreferenceManager(context)
    }
}
