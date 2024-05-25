package com.yessorae.data.di

import com.yessorae.data.source.network.polygon.util.ChartTrainerDatabaseTransactionHelper
import com.yessorae.data.source.network.polygon.util.DatabaseTransactionHelper
import com.yessorae.data.source.network.polygon.util.DefaultChartRequestArgumentHelper
import com.yessorae.data.util.DefaultChartTrainerLoggerImpl
import com.yessorae.domain.common.ChartRequestArgumentHelper
import com.yessorae.domain.common.ChartTrainerLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {
    @Binds
    @Singleton
    abstract fun bindsRandomChartArgumentGenerator(
        defaultRandomChartArgumentGenerator: DefaultChartRequestArgumentHelper
    ): ChartRequestArgumentHelper

    @Binds
    @Singleton
    abstract fun bindsChartTrainerDatabaseTransactionHelper(
        chartTrainerDatabaseTransactionHelper: ChartTrainerDatabaseTransactionHelper
    ): DatabaseTransactionHelper

    @Binds
    @Singleton
    abstract fun bindsDefaultChartTrainerLogger(
        chartTrainerLogger: DefaultChartTrainerLoggerImpl
    ): ChartTrainerLogger
}
