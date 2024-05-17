package com.yessorae.data.source.network.polygon

import com.yessorae.data.source.network.ChartNetworkDataSource
import com.yessorae.data.source.network.polygon.api.PolygonChartApi
import com.yessorae.data.source.network.polygon.common.PolygonConstant.TIME_SPAN_DAY_PATH_VALUE
import com.yessorae.data.source.network.polygon.common.PolygonConstant.TIME_SPAN_HOUR_PATH_VALUE
import com.yessorae.data.source.network.polygon.model.chart.asDomainModel
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit
import javax.inject.Inject

class PolygonChartNetworkDataSource @Inject constructor(
    private val api: PolygonChartApi
) : ChartNetworkDataSource {
    override suspend fun getChart(
        ticker: String,
        tickUnit: TickUnit,
        from: String,
        to: String
    ): Chart {
        return api
            .getChartData(
                ticker = ticker,
                timeSpan = tickUnit.toPathValue(),
                from = from,
                to = to
            )
            .asDomainModel(tickUnit = tickUnit)
    }
}

private fun TickUnit.toPathValue(): String {
    return when (this) {
        TickUnit.DAY -> TIME_SPAN_DAY_PATH_VALUE
        TickUnit.HOUR -> TIME_SPAN_HOUR_PATH_VALUE
    }
}
