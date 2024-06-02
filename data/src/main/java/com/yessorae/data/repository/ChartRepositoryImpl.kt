package com.yessorae.data.repository

import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.ChartNetworkDataSource
import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.local.database.model.asEntity
import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.data.source.network.polygon.model.chart.asDomainModel
import com.yessorae.domain.common.ChartRequestArgumentHelper
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.ChartRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ChartRepositoryImpl @Inject constructor(
    private val networkDataSource: ChartNetworkDataSource,
    private val localDBDataSource: ChartTrainerLocalDBDataSource,
    private val appPreferences: ChartTrainerPreferencesDataSource,
    private val chartRequestArgumentHelper: ChartRequestArgumentHelper,
    @Dispatcher(ChartTrainerDispatcher.IO)
    private val dispatcher: CoroutineDispatcher
) : ChartRepository {
    override suspend fun fetchNewChartRandomly(): Chart =
        withContext(dispatcher) {
            val chart = networkDataSource
                .getChart(
                    ticker = chartRequestArgumentHelper.getRandomTicker(),
                    tickUnit = appPreferences.getTickUnit(),
                    from = chartRequestArgumentHelper.getFromDate(),
                    to = chartRequestArgumentHelper.getToDate()
                )
                .asDomainModel(TickUnit.DAY)

            val chartId = localDBDataSource.insertChart(chart.asEntity())
            localDBDataSource.insertTicks(chart.ticks.map { it.asEntity(chartId = chartId) })
            chart.copy(id = chartId)
        }
}
