package com.yessorae.data.di

import com.yessorae.data.source.network.ChartNetworkDataSource
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
    abstract fun bindsChartNetworkDataSource(
        polygonChartNetworkDataSource: PolygonChartNetworkDataSource
    ): ChartNetworkDataSource
}
