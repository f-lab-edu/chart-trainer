package com.yessorae.data.source.network.polygon.model.chart

import com.google.gson.annotations.SerializedName
import com.yessorae.data.util.toLocalDateTime
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit

data class ChartDto(
    val ticker: String,
    val adjusted: Boolean,
    val count: Int,
    val queryCount: Int,
    @SerializedName("request_id")
    val requestId: String,
    @SerializedName("results")
    val ticks: List<TickDto>?,
    @SerializedName("resultsCount")
    val ticksCount: Int,
    val status: String
)

fun ChartDto.asDomainModel(tickUnit: TickUnit): Chart {
    return Chart(
        tickerSymbol = ticker,
        startDateTime = ticks?.firstOrNull()?.startTimestamp?.toLocalDateTime(),
        endDateTime = ticks?.lastOrNull()?.startTimestamp?.toLocalDateTime(),
        ticks = ticks?.map(TickDto::asDomainModel) ?: listOf(),
        tickUnit = tickUnit
    )
}
