package com.yessorae.data.repository

import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.data.source.network.ChartNetworkDataSource
import com.yessorae.data.source.network.polygon.model.chart.asDomainModel
import com.yessorae.domain.common.ChartRequestArgumentHelper
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.ChartRepository
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val networkDataSource: ChartNetworkDataSource,
    private val appPreferences: ChartTrainerPreferencesDataSource,
    private val chartRequestArgumentHelper: ChartRequestArgumentHelper
) : ChartRepository {
    override suspend fun fetchNewChartRandomly(): Chart {
        return networkDataSource
            .getChart(
                ticker = chartRequestArgumentHelper.getRandomTicker(),
                tickUnit = appPreferences.getTickUnit(),
                from = chartRequestArgumentHelper.getStartDate(),
                to = chartRequestArgumentHelper.getEndDate()
            )
            .asDomainModel(tickUnit = TickUnit.DAY)
    }
}
