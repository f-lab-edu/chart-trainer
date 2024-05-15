package com.yessorae.data.source.network.polygon.model.chart

import com.google.gson.annotations.SerializedName
import com.yessorae.data.common.toLocalDateTime
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit

data class ChartDto(
    val adjusted: Boolean,
    val count: Int,
    val queryCount: Int,
    @SerializedName("request_id")
    val requestId: String,
    @SerializedName("results")
    val ticks: List<TickDto>,
    @SerializedName("resultsCount")
    val ticksCount: Int,
    val status: String,
    val ticker: String
)

fun ChartDto.asDomainModel(tickUnit: TickUnit): Chart {
    return Chart(
        tickerSymbol = ticker,
        startDateTime = ticks.first().startTimestamp.toLocalDateTime(),
        endDateTime = ticks.last().startTimestamp.toLocalDateTime(),
        ticks = ticks.map(TickDto::asDomainModel),
        tickUnit = tickUnit
    )
}