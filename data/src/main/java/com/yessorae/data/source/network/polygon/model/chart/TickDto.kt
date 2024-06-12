package com.yessorae.data.source.network.polygon.model.chart

import com.yessorae.data.util.toLocalDateTime
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.value.Money
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TickDto(
    @SerialName("c")
    val closePrice: Double,
    @SerialName("h")
    val maxPrice: Double,
    @SerialName("l")
    val minPrice: Double,
    @SerialName("n")
    val transactionCount: Int = 0,
    @SerialName("o")
    val openPrice: Double,
    @SerialName("t")
    val startTimestamp: Long,
    @SerialName("v")
    val tradingVolume: Double,
    @SerialName("vw")
    val volumeWeightedAveragePrice: Double = 0.0
)

internal fun TickDto.asDomainModel() =
    Tick(
        openPrice = Money(openPrice),
        closePrice = Money(closePrice),
        maxPrice = Money(maxPrice),
        minPrice = Money(minPrice),
        transactionCount = transactionCount,
        startTimestamp = startTimestamp.toLocalDateTime(),
        tradingVolume = tradingVolume.toInt(),
        volumeWeightedAveragePrice = Money(volumeWeightedAveragePrice)
    )
