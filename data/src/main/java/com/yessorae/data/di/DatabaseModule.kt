package com.yessorae.data.di

import android.content.Context
import androidx.room.Room
import com.yessorae.data.source.local.database.ChartTrainerDatabase
import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.dao.TradeDao
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
            name = ChartTrainerDatabase.NAME
        ).build()

    @Provides
    fun providesChartDao(database: ChartTrainerDatabase): ChartDao = database.getChartDao()

    @Provides
    fun provideChartGameDao(database: ChartTrainerDatabase): ChartGameDao = database.getChartGameDao()

    @Provides
    fun provideTickDao(database: ChartTrainerDatabase): TickDao = database.getTickDao()

    @Provides
    fun provideTradeDao(database: ChartTrainerDatabase): TradeDao = database.getTradeDao()
}
