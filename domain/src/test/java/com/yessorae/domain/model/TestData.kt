package com.yessorae.domain.model

import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.entity.value.asMoney
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
private val defaultMoney = DEFAULT_DOUBLE.asMoney()

val baseClosePrice: Money = 150.asMoney()
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
    currentTurn: Int = DEFAULT_INT,
    totalTurn: Int = DEFAULT_INT,
    startBalance: Money = defaultMoney,
    currentBalance: Money = defaultMoney,
    closeStockPrice: Money = defaultMoney,
    isQuit: Boolean = Random.nextBoolean(),
    totalStockCount: Int = DEFAULT_INT,
    averageStockPrice: Money = defaultMoney,
    accumulatedTotalProfit: Money = defaultMoney
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
    ownedStockCount: Int = DEFAULT_INT,
    ownedAverageStockPrice: Money = defaultMoney,
    stockPrice: Money = defaultMoney,
    count: Int = DEFAULT_INT,
    turn: Int = DEFAULT_INT,
    type: TradeType = TradeType.BUY,
    commissionRate: Double = DEFAULT_DOUBLE
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

fun createTestUser(
    balance: Money = defaultMoney,
    winCount: Int = DEFAULT_INT,
    loseCount: Int = DEFAULT_INT,
    averageRateOfProfit: Double = DEFAULT_DOUBLE
) = User(
    balance = balance,
    winCount = winCount,
    loseCount = loseCount,
    averageRateOfProfit = averageRateOfProfit
)
