package com.yessorae.data.di

import com.yessorae.data.source.ChartNetworkDataSource
import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.ChartTrainerPreferencesDataSource
import com.yessorae.data.source.local.database.ChartTrainerLocalDBDataSourceImpl
import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSourceImpl
import com.yessorae.data.source.network.polygon.PolygonChartNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsChartNetworkDataSource(polygonChartNetworkDataSource: PolygonChartNetworkDataSource): ChartNetworkDataSource

    @Binds
    @Singleton
    abstract fun bindsChartTrainerLocalDBDataSource(chartTrainerLocalDBDataSource: ChartTrainerLocalDBDataSourceImpl): ChartTrainerLocalDBDataSource

    @Binds
    @Singleton
    abstract fun bindsChartTrainerPreferenceDataSource(
        chartTrainerPreferencesDataSource: ChartTrainerPreferencesDataSourceImpl
    ): ChartTrainerPreferencesDataSource
}
