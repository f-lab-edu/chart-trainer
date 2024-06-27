package com.yessorae.domain.model

import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import java.time.LocalDateTime

const val TEST_CHART_GAME_ID = 1L
const val TEST_CHART_ID = 2L
const val TEST_TICKER_SYMBOL = "AAPL"
val testStartDate: LocalDateTime = LocalDateTime.of(2024, 5, 29, 0, 0)
val testEndDate: LocalDateTime = LocalDateTime.of(2022, 5, 29, 0, 0)

val baseClosePrice: Money = Money.of(150.0)
const val TICK_COUNT = 10
val testClosePrice: Money = baseClosePrice * TICK_COUNT

fun createTestTick(index: Int) =
    Tick(
        openPrice = Money.of(100.0) * index,
        maxPrice = Money.of(200.0) * index,
        minPrice = Money.of(50.0) * index,
        closePrice = baseClosePrice * index,
        transactionCount = 1000 * index,
        startTimestamp = testStartDate.plusDays(index.toLong()),
        tradingVolume = 1000 * index,
        volumeWeightedAveragePrice = Money.of(150.0) * index
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
    currentTurn: Int = 1,
    totalTurn: Int = 50,
    startBalance: Money = Money.of(1000.0),
    currentBalance: Money = Money.of(1000.0),
    closeStockPrice: Money = Money.of(100.0),
    isQuit: Boolean = false,
    totalStockCount: Int = 0,
    totalStockPrice: Money = Money.of(0.0),
    averageStockPrice: Money = Money.of(0.0),
    accumulatedTotalProfit: Money = Money.of(0.0)
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
    totalStockPrice = totalStockPrice,
    averageStockPrice = averageStockPrice,
    accumulatedTotalProfit = accumulatedTotalProfit
)

fun createTestTrade(
    gameId: Long = TEST_CHART_GAME_ID,
    ownedStockCount: Int = Int.MAX_VALUE,
    ownedAverageStockPrice: Money = Money.of(Double.MAX_VALUE),
    stockPrice: Money = Money.of(Double.MAX_VALUE),
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
