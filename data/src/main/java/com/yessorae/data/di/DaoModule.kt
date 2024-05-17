package com.yessorae.data.di

import com.yessorae.data.source.local.database.ChartTrainerDatabase
import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.dao.TradeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesChartDao(database: ChartTrainerDatabase): ChartDao = database.getChartDao()

    @Provides
    fun provideChartGameDao(database: ChartTrainerDatabase): ChartGameDao =
        database.getChartGameDao()

    @Provides
    fun provideTickDao(database: ChartTrainerDatabase): TickDao = database.getTickDao()

    @Provides
    fun provideTradeDao(database: ChartTrainerDatabase): TradeDao = database.getTradeDao()
}
