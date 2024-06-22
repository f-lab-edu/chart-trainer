package com.yessorae.domain.model

import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import java.time.LocalDateTime
import kotlin.random.Random

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
    currentTurn = Int.MAX_VALUE,
    totalTurn = Int.MAX_VALUE,
    startBalance = Money.of(Double.MAX_VALUE),
    currentBalance = Money.of(Double.MAX_VALUE),
    closeStockPrice = Money.of(Double.MAX_VALUE),
    isQuit = Random.nextBoolean(),
    totalStockCount = Int.MAX_VALUE,
    averageStockPrice = Money.of(Double.MAX_VALUE),
    accumulatedTotalProfit = Money.of(Double.MAX_VALUE)
)

val baseTestTrade = Trade.new(
    gameId = TEST_CHART_GAME_ID,
    ownedStockCount = Int.MAX_VALUE,
    ownedAverageStockPrice = Money.of(Double.MAX_VALUE),
    stockPrice = Money.of(Double.MAX_VALUE),
    count = Int.MAX_VALUE,
    turn = Int.MAX_VALUE,
    type = TradeType.BUY,
    commissionRate = Double.MAX_VALUE
)
