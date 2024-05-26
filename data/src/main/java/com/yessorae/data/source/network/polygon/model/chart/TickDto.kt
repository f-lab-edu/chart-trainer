package com.yessorae.data.source.network.polygon.model.chart

import com.google.gson.annotations.SerializedName
import com.yessorae.data.util.toLocalDateTime
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.value.Money

data class TickDto(
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
    val startTimestamp: Long,
    @SerializedName("v")
    val tradingVolume: Int,
    @SerializedName("vw")
    val volumeWeightedAveragePrice: Double
)

internal fun TickDto.asDomainModel() =
    Tick(
        openPrice = Money(openPrice),
        closePrice = Money(closePrice),
        maxPrice = Money(maxPrice),
        minPrice = Money(minPrice),
        transactionCount = transactionCount,
        startTimestamp = startTimestamp.toLocalDateTime(),
        tradingVolume = tradingVolume,
        volumeWeightedAveragePrice = Money(volumeWeightedAveragePrice)
    )
