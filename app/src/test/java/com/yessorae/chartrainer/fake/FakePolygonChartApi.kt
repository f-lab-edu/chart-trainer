package com.yessorae.chartrainer.fake

import com.yessorae.data.source.network.polygon.api.PolygonChartApi
import com.yessorae.data.source.network.polygon.model.chart.ChartDto

class FakePolygonChartApi(
) : PolygonChartApi {
    private val tickerToDtoMap: MutableMap<String, ChartDto> = mutableMapOf()

    override suspend fun getChartData(
        ticker: String,
        timeSpan: String,
        from: String,
        to: String,
        multiplier: Int,
        adjusted: Boolean,
        sort: String,
        apiKey: String
    ): ChartDto = tickerToDtoMap[ticker]!!

    fun setTickerToDto(
        ticker: String,
        dto: ChartDto
    ) {
        tickerToDtoMap[ticker] = dto
    }
}
