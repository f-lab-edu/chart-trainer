package com.yessorae.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val Context.appDatastore: DataStore<Preferences> by preferencesDataStore(
        name = "chart_trainer_app_preference"
    )

    @Provides
    @Singleton
    fun providesAppPreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.appDatastore
}
