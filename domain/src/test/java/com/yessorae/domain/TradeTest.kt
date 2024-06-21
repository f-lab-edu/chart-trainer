package com.yessorae.domain

import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

const val TEST_CHART_GAME_ID = 1L
const val TEST_CHART_ID = 2L
const val TEST_TICKER_SYMBOL = "AAPL"
const val TEST_COMMISSION_RATE = 0.1
val testStartDate: LocalDateTime = LocalDateTime.of(2024, 5, 29, 0, 0)
val testEndDate: LocalDateTime = LocalDateTime.of(2022, 5, 29, 0, 0)
val testClosePrice: Money = Money.of(100.0)
val tickListSize = 10

fun createTestTick(index: Int) = Tick(
    openPrice = Money.of(100.0) * index,
    maxPrice = Money.of(200.0) * index,
    minPrice = Money.of(50.0) * index,
    closePrice = Money.of(150.0) * index,
    transactionCount = 1000 * index,
    startTimestamp = testStartDate.plusDays(index.toLong()),
    tradingVolume = 1000 * index,
    volumeWeightedAveragePrice = Money.of(150.0) * index
)

val testTicks = (1..tickListSize).map { index -> createTestTick(index) }

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

val baseChart = Chart(
    id = TEST_CHART_ID,
    tickerSymbol = TEST_TICKER_SYMBOL,
    startDateTime = testStartDate,
    endDateTime = testEndDate,
    ticks = testTicks,
    tickUnit = TickUnit.DAY
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

class TradeTest {

    @Test
    fun profit_sell_calculation() {
        // 익절
        val trade = baseTestTrade.copy(
            type = TradeType.SELL,
            ownedAverageStockPrice = Money.of(40_000.0),
            ownedStockCount = 10,
            stockPrice = Money.of(50_000.0),
            count = 5,
            commissionRate = 0.1
        )

        assertEquals(Money.of(250_000.0), trade.totalTradeMoney)
        assertEquals(Money.of(250.0), trade.commission)
        assertEquals(Money.of(49_750.0), trade.profit)
    }

    @Test
    fun stop_loss_sell_calculation() {
        // 손절
        val trade = baseTestTrade.copy(
            type = TradeType.SELL,
            ownedAverageStockPrice = Money.of(50_000.0),
            ownedStockCount = 10,
            stockPrice = Money.of(40_000.0),
            count = 5,
            commissionRate = 0.1
        )

        assertEquals(Money.of(200_000.0), trade.totalTradeMoney)
        assertEquals(Money.of(200.0), trade.commission)
        assertEquals(Money.of(-50_200.0), trade.profit)
    }


    @Test
    fun buy_profit_calculation() {
        // 매수
        val trade = baseTestTrade.copy(
            type = TradeType.BUY,
            stockPrice = Money.of(50_000.0),
            commissionRate = 0.1,
            count = 100
        )

        assertEquals(Money.of(-5_000.0), trade.profit)
    }
}
