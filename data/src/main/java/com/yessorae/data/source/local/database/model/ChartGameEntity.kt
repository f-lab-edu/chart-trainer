package com.yessorae.data.source.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.value.Money

@Entity(ChartGameEntity.NAME)
data class ChartGameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = COL_CHART_ID)
    val chartId: Long,
    @ColumnInfo(name = COL_CURRENT_TURN)
    val currentTurn: Int,
    @ColumnInfo(name = COL_LAST_VISIBLE_TICK_INDEX)
    val lastVisibleTickIndex: Int,
    @ColumnInfo(name = COL_TOTAL_TURN)
    val totalTurn: Int,
    @ColumnInfo(name = COL_START_BALANCE)
    val startBalance: Money,
    @ColumnInfo(name = COL_CURRENT_BALANCE)
    val currentBalance: Money,
    @ColumnInfo(name = COL_IS_QUIT)
    val isQuit: Boolean,
    @ColumnInfo(name = COL_CLOSE_STOCK_PRICE)
    val closeStockPrice: Money,
    @ColumnInfo(name = COL_TOTAL_STOCK_COUNT)
    val totalStockCount: Int,
    @ColumnInfo(name = COL_TOTAL_STOCK_PRICE)
    val totalStockPrice: Money,
    @ColumnInfo(name = COL_AVERAGE_STOCK_PRICE)
    val averageStockPrice: Money,
    @ColumnInfo(name = COL_ACCUMULATED_TOTAL_PROFIT)
    val accumulatedTotalProfit: Money
) {
    companion object {
        const val NAME = "chart_game_table"
        const val COL_CHART_ID = "chart_id"
        const val COL_CURRENT_TURN = "current_turn"
        const val COL_LAST_VISIBLE_TICK_INDEX = "last_visible_tick_index"
        const val COL_TOTAL_TURN = "total_turn"
        const val COL_START_BALANCE = "start_balance"
        const val COL_CURRENT_BALANCE = "current_balance"
        const val COL_IS_QUIT = "is_quit"
        const val COL_CLOSE_STOCK_PRICE = "close_stock_price"
        const val COL_TOTAL_STOCK_COUNT = "total_stock_count"
        const val COL_TOTAL_STOCK_PRICE = "total_stock_price"
        const val COL_AVERAGE_STOCK_PRICE = "average_stock_price"
        const val COL_ACCUMULATED_TOTAL_PROFIT = "accumulated_total_profit"
    }
}

fun ChartGame.asEntity() =
    ChartGameEntity(
        id = id,
        chartId = chartId,
        currentTurn = currentTurn,
        lastVisibleTickIndex = lastVisibleTickIndex,
        totalTurn = totalTurn,
        startBalance = startBalance,
        currentBalance = currentBalance,
        isQuit = isQuit,
        closeStockPrice = closeStockPrice,
        totalStockCount = totalStockCount,
        totalStockPrice = totalStockPrice,
        averageStockPrice = averageStockPrice,
        accumulatedTotalProfit = accumulatedTotalProfit
    )

fun ChartGameEntity.asDomainModel() =
    ChartGame(
        id = id,
        chartId = chartId,
        currentTurn = currentTurn,
        lastVisibleTickIndex = lastVisibleTickIndex,
        totalTurn = totalTurn,
        startBalance = startBalance,
        currentBalance = currentBalance,
        isQuit = isQuit,
        closeStockPrice = closeStockPrice,
        totalStockCount = totalStockCount,
        averageStockPrice = averageStockPrice,
        accumulatedTotalProfit = accumulatedTotalProfit
    )
