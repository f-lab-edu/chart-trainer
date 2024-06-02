package com.yessorae.data.source.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.value.Money
import java.time.LocalDateTime

@Entity(tableName = TickEntity.NAME)
data class TickEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = COL_CHART_ID)
    val chartId: Long,
    @ColumnInfo(name = COL_OPEN_PRICE)
    val openPrice: Double,
    @ColumnInfo(name = COL_MAX_PRICE)
    val maxPrice: Double,
    @ColumnInfo(name = COL_MIN_PRICE)
    val minPrice: Double,
    @ColumnInfo(name = COL_CLOSE_PRICE)
    val closePrice: Double,
    @ColumnInfo(name = COL_TRANSACTION_COUNT)
    val transactionCount: Int,
    @ColumnInfo(name = COL_START_TIMESTAMP)
    val startTimestamp: LocalDateTime,
    @ColumnInfo(name = COL_TRADING_VOLUME)
    val tradingVolume: Int,
    @ColumnInfo(name = COL_VOLUME_WEIGHTED_AVERAGE_PRICE)
    val volumeWeightedAveragePrice: Double
) {
    companion object {
        const val NAME = "table_tick"
        const val COL_CHART_ID = "chart_id"
        const val COL_OPEN_PRICE = "open_price"
        const val COL_MAX_PRICE = "max_price"
        const val COL_MIN_PRICE = "min_price"
        const val COL_CLOSE_PRICE = "close_price"
        const val COL_TRANSACTION_COUNT = "transaction_count"
        const val COL_START_TIMESTAMP = "start_timestamp"
        const val COL_TRADING_VOLUME = "trading_volume"
        const val COL_VOLUME_WEIGHTED_AVERAGE_PRICE = "volume_weighted_average_price"
    }
}

fun TickEntity.asDomainModel() =
    Tick(
        openPrice = Money(openPrice),
        maxPrice = Money(maxPrice),
        minPrice = Money(minPrice),
        closePrice = Money(closePrice),
        transactionCount = transactionCount,
        startTimestamp = startTimestamp,
        tradingVolume = tradingVolume,
        volumeWeightedAveragePrice = Money(volumeWeightedAveragePrice)
    )
