package com.yessorae.data.source.network.polygon.model.chart

import com.google.gson.annotations.SerializedName
import com.yessorae.data.common.toLocalDateTime
import com.yessorae.domain.entity.tick.Tick

data class TickDto( // TODO SR-N 이름 수정
    @SerializedName("c")
    val closePrice: Double,
    @SerializedName("h")
    val maxPrice: Double,
    @SerializedName("l")
    val minPrice: Double,
    @SerializedName("n")
    val transactionCount: Int,
    @SerializedName("o")
    val openPrice: Double,
    @SerializedName("t")
    // The Unix Msec timestamp for the start of the aggregate window.
    val startTimestamp: Long,
    @SerializedName("v")
    val tradingVolume: Int,
    @SerializedName("vw")
    val volumeWeightedAveragePrice: Double
)

internal fun TickDto.asDomainModel() = Tick(
    openPrice = openPrice,
    closePrice = closePrice,
    maxPrice = maxPrice,
    minPrice = minPrice,
    transactionCount = transactionCount,
    startTimestamp = startTimestamp.toLocalDateTime(),
    tradingVolume = tradingVolume,
    volumeWeightedAveragePrice = volumeWeightedAveragePrice
)