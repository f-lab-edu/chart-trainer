package com.yessorae.chartrainer.fake.model

import com.yessorae.data.source.network.polygon.model.chart.ChartDto
import com.yessorae.data.source.network.polygon.model.chart.TickDto

fun createChartDto(
    ticker: String = "AAPL",
    adjusted: Boolean = true,
    queryCount: Int = 10,
    requestId: String = "123456789",
    ticks: List<TickDto> = listOf(
        createTickDto(),
        createTickDto(),
        createTickDto()
    ),
    ticksCount: Int = 3,
    status: String = "success"
): ChartDto {
    return ChartDto(
        ticker = ticker,
        adjusted = adjusted,
        queryCount = queryCount,
        requestId = requestId,
        ticks = ticks,
        ticksCount = ticksCount,
        status = status
    )
}

fun createTickDto(
    closePrice: Double = 100.0,
    maxPrice: Double = 105.0,
    minPrice: Double = 95.0,
    openPrice: Double = 98.0,
    startTimestamp: Long = System.currentTimeMillis(),
    tradingVolume: Double = 10000.0,
    volumeWeightedAveragePrice: Double = 100.0,
    transactionCount: Int = 100
): TickDto {
    return TickDto(
        closePrice = closePrice,
        maxPrice = maxPrice,
        minPrice = minPrice,
        openPrice = openPrice,
        startTimestamp = startTimestamp,
        tradingVolume = tradingVolume,
        volumeWeightedAveragePrice = volumeWeightedAveragePrice,
        transactionCount = transactionCount
    )
}
