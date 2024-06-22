package com.yessorae.domain.model

import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import java.time.LocalDateTime
import kotlin.random.Random

private const val TEST_CHART_GAME_ID = 1L
private const val TEST_CHART_ID = 2L
private const val TEST_TICKER_SYMBOL = "AAPL"
private const val TICK_COUNT = 10
private val testStartDate: LocalDateTime = LocalDateTime.of(2024, 5, 29, 0, 0)
private val testEndDate: LocalDateTime = LocalDateTime.of(2022, 5, 29, 0, 0)
private const val DEFAULT_INT = Int.MAX_VALUE
private const val DEFAULT_DOUBLE = Double.MAX_VALUE
private val defaultMoney = Money.of(DEFAULT_DOUBLE)

private val baseClosePrice: Money = Money.of(150.0)
private val testClosePrice: Money = baseClosePrice * TICK_COUNT

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
    currentTurn = DEFAULT_INT,
    totalTurn = DEFAULT_INT,
    startBalance = defaultMoney,
    currentBalance = defaultMoney,
    closeStockPrice = defaultMoney,
    isQuit = Random.nextBoolean(),
    totalStockCount = DEFAULT_INT,
    averageStockPrice = defaultMoney,
    accumulatedTotalProfit = defaultMoney
)

val baseTestTrade = Trade.new(
    gameId = TEST_CHART_GAME_ID,
    ownedStockCount = DEFAULT_INT,
    ownedAverageStockPrice = defaultMoney,
    stockPrice = defaultMoney,
    count = DEFAULT_INT,
    turn = DEFAULT_INT,
    type = TradeType.BUY,
    commissionRate = DEFAULT_DOUBLE
)

val baseTestUser = User(
    balance = defaultMoney,
    winCount = DEFAULT_INT,
    loseCount = DEFAULT_INT,
    averageRateOfProfit = DEFAULT_DOUBLE
)
