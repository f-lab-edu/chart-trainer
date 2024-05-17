package com.yessorae.data.di

import android.content.Context
import androidx.room.Room
import com.yessorae.data.source.local.database.ChartTrainerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesChartTrainerDatabase(@ApplicationContext context: Context): ChartTrainerDatabase =
        Room.databaseBuilder(
            context = context,
            klass = ChartTrainerDatabase::class.java,
            name = "chart-trainer-database"
        ).build()
}
