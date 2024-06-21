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

val testTicks = (1..TICK_COUNT).map { index -> createTestTick(index = index) }

val baseChart = Chart(
    id = TEST_CHART_ID,
    tickerSymbol = TEST_TICKER_SYMBOL,
    startDateTime = testStartDate,
    endDateTime = testEndDate,
    ticks = testTicks,
    tickUnit = TickUnit.DAY
)

val baseTestChartGame = ChartGame(
    id = TEST_CHART_GAME_ID,
    chartId = TEST_CHART_ID,
    currentTurn = 1,
    totalTurn = 50,
    startBalance = Money.of(1000.0),
    currentBalance = Money.of(1000.0),
    closeStockPrice = Money.of(100.0),
    isQuit = false,
    totalStockCount = 0,
    totalStockPrice = Money.of(0.0),
    averageStockPrice = Money.of(0.0),
    accumulatedTotalProfit = Money.of(0.0)
)

val baseTestTrade = Trade.new(
    gameId = TEST_CHART_GAME_ID,
    ownedStockCount = 0,
    ownedAverageStockPrice = Money.of(0.0),
    stockPrice = Money.of(0.0),
    count = 0,
    turn = 0,
    type = TradeType.BUY,
    commissionRate = 0.1
)
