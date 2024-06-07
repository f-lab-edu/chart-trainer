package com.yessorae.data.source.network.polygon.model.chart

import com.yessorae.data.util.toLocalDateTime
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChartDto(
    val ticker: String,
    val adjusted: Boolean,
    val count: Int = 0,
    val queryCount: Int,
    @SerialName("request_id")
    val requestId: String,
    @SerialName("results")
    val ticks: List<TickDto> = listOf(),
    @SerialName("resultsCount")
    val ticksCount: Int = 0,
    val status: String
)

fun ChartDto.asDomainModel(tickUnit: TickUnit): Chart {
    return Chart(
        tickerSymbol = ticker,
        startDateTime = ticks.firstOrNull()?.startTimestamp?.toLocalDateTime(),
        endDateTime = ticks.lastOrNull()?.startTimestamp?.toLocalDateTime(),
        ticks = ticks.map(TickDto::asDomainModel),
        tickUnit = tickUnit
    )
}
