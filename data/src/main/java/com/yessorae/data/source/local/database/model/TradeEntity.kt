package com.yessorae.data.source.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money

@Entity(tableName = TradeEntity.NAME)
data class TradeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = COL_GAME_ID)
    val gameId: Long,
    @ColumnInfo(name = COL_OWNED_AVERAGE_STOCK_PRICE)
    val ownedAverageStockPrice: Money,
    @ColumnInfo(name = COL_STOCK_PRICE)
    val stockPrice: Money,
    @ColumnInfo(name = COL_COUNT)
    val count: Int,
    @ColumnInfo(name = COL_TURN)
    val turn: Int,
    @ColumnInfo(name = COL_TYPE)
    val type: TradeType,
    @ColumnInfo(name = COL_COMMISSION_RATE)
    val commissionRate: Double
) {
    companion object {
        const val NAME = "trade_table"
        const val COL_GAME_ID = "game_id"
        const val COL_OWNED_AVERAGE_STOCK_PRICE = "owned_average_price"
        const val COL_STOCK_PRICE = "stock_price"
        const val COL_COUNT = "count"
        const val COL_TURN = "turn"
        const val COL_TYPE = "type"
        const val COL_COMMISSION_RATE = "commission_rate"
    }
}

fun Trade.asEntity() =
    TradeEntity(
        id = id,
        gameId = gameId,
        ownedAverageStockPrice = ownedAverageStockPrice,
        stockPrice = stockPrice,
        count = count,
        turn = turn,
        type = type,
        commissionRate = commissionRate
    )

fun TradeEntity.asDomainModel() =
    Trade(
        id = id,
        gameId = gameId,
        ownedAverageStockPrice = ownedAverageStockPrice,
        stockPrice = stockPrice,
        count = count,
        turn = turn,
        type = type,
        commissionRate = commissionRate
    )
