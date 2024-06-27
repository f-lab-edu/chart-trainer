package com.yessorae.domain.model

import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.entity.value.asMoney
import java.time.LocalDateTime

const val TEST_CHART_GAME_ID = 1L
const val TEST_CHART_ID = 2L
const val TEST_TICKER_SYMBOL = "AAPL"
val testStartDate: LocalDateTime = LocalDateTime.of(2024, 5, 29, 0, 0)
val testEndDate: LocalDateTime = LocalDateTime.of(2022, 5, 29, 0, 0)

val baseClosePrice: Money = 150.asMoney()
const val TICK_COUNT = 10
val testClosePrice: Money = baseClosePrice * TICK_COUNT

fun createTestTick(index: Int) =
    Tick(
        openPrice = 100.asMoney() * index,
        maxPrice = 200.asMoney() * index,
        minPrice = 50.asMoney() * index,
        closePrice = baseClosePrice * index,
        transactionCount = 1000 * index,
        startTimestamp = testStartDate.plusDays(index.toLong()),
        tradingVolume = 1000 * index,
        volumeWeightedAveragePrice = 150.asMoney() * index
    )

fun createTestTicks() = (1..TICK_COUNT).map { index -> createTestTick(index = index) }

fun createTestChart(
    id: Long = TEST_CHART_ID,
    tickerSymbol: String = TEST_TICKER_SYMBOL,
    startDateTime: LocalDateTime = testStartDate,
    endDateTime: LocalDateTime = testEndDate,
    ticks: List<Tick> = createTestTicks(),
    tickUnit: TickUnit = TickUnit.DAY
) = Chart(
    id = id,
    tickerSymbol = tickerSymbol,
    startDateTime = startDateTime,
    endDateTime = endDateTime,
    ticks = ticks,
    tickUnit = tickUnit
)

fun createTestChartGame(
    id: Long = TEST_CHART_GAME_ID,
    chartId: Long = TEST_CHART_ID,
    currentTurn: Int = Int.MAX_VALUE,
    totalTurn: Int = Int.MAX_VALUE,
    startBalance: Money = Int.MAX_VALUE.asMoney(),
    currentBalance: Money = Int.MAX_VALUE.asMoney(),
    closeStockPrice: Money = Int.MAX_VALUE.asMoney(),
    isQuit: Boolean = false,
    totalStockCount: Int = Int.MAX_VALUE,
    averageStockPrice: Money = Int.MAX_VALUE.asMoney(),
    accumulatedTotalProfit: Money = Int.MAX_VALUE.asMoney()
) = ChartGame(
    id = id,
    chartId = chartId,
    currentTurn = currentTurn,
    totalTurn = totalTurn,
    startBalance = startBalance,
    currentBalance = currentBalance,
    closeStockPrice = closeStockPrice,
    isQuit = isQuit,
    totalStockCount = totalStockCount,
    averageStockPrice = averageStockPrice,
    accumulatedTotalProfit = accumulatedTotalProfit
)

fun createTestTrade(
    gameId: Long = TEST_CHART_GAME_ID,
    ownedStockCount: Int = Int.MAX_VALUE,
    ownedAverageStockPrice: Money = Double.MAX_VALUE.asMoney(),
    stockPrice: Money = Double.MAX_VALUE.asMoney(),
    count: Int = Int.MAX_VALUE,
    turn: Int = Int.MAX_VALUE,
    type: TradeType = TradeType.BUY,
    commissionRate: Double = Double.MAX_VALUE
) = Trade(
    gameId = gameId,
    ownedStockCount = ownedStockCount,
    ownedAverageStockPrice = ownedAverageStockPrice,
    stockPrice = stockPrice,
    count = count,
    turn = turn,
    type = type,
    commissionRate = commissionRate
)
