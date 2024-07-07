package com.yessorae.chartrainer.fake.model

import com.yessorae.data.source.network.polygon.model.chart.ChartDto
import com.yessorae.data.source.network.polygon.model.chart.TickDto

fun createChartDto(
    ticker: String,
    ticks: List<TickDto> = listOf(),
    ticksCount: Int = ticks.size,

    adjusted: Boolean = true,
    queryCount: Int = 10,
    requestId: String = "123456789",
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
    startTimestamp: Long = 1L,
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

/**
 * 다양한 값이 필요하지 않을 때 간소하게 테스트하기 위한 Factory 함수
 */
fun createTickDto(
    singleValue: Double,
): TickDto {
    return TickDto(
        closePrice = singleValue,
        maxPrice = singleValue,
        minPrice = singleValue,
        openPrice = singleValue,
        startTimestamp = singleValue.toLong(),
        tradingVolume = singleValue,
        volumeWeightedAveragePrice = singleValue,
        transactionCount = singleValue.toInt()
    )
}
