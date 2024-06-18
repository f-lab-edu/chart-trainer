package com.yessorae.presentation.domain.model

import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import java.time.LocalDateTime


fun createMockTrade(
    gameId: Long = 1,
    // 가지고 있던 평단가가 50000인데
    ownedAverageStockPrice: Money = Money(50000.0),
    // 55000에
    stockPrice: Money = Money(55000.0),
    // 100주를
    count: Int = 100,
    turn: Int = 5,
    // 매수
    type: TradeType = TradeType.BUY,
    commissionRate: Double = 0.002
): Trade {
    return Trade.new(
        gameId = gameId,
        ownedAverageStockPrice = ownedAverageStockPrice,
        stockPrice = stockPrice,
        count = count,
        turn = turn,
        type = type,
        commissionRate = commissionRate
    )
}

fun createMockChartGame(
    id: Long = 1,
    chart: Chart = createMockChart(),
    trades: List<Trade> = listOf(
        createMockTrade(),
        createMockTrade(
            type = TradeType.SELL,
            stockPrice = Money(60000.0),
            count = 50
        )
    ),
    currentTurn: Int = 1,
    totalTurn: Int = 10,
    startBalance: Money = Money(1000000.0),
    currentBalance: Money = Money(1000000.0),
    isQuit: Boolean = false
): ChartGame {
    return ChartGame(
        id = id,
        chart = chart,
        trades = trades,
        currentTurn = currentTurn,
        totalTurn = totalTurn,
        startBalance = startBalance,
        currentBalance = currentBalance,
        isQuit = isQuit
    )
}

fun createMockChart(
    id: Long = 1,
    tickerSymbol: String = "AAPL",
    startDateTime: LocalDateTime? = LocalDateTime.now().minusDays(30),
    endDateTime: LocalDateTime? = LocalDateTime.now(),
    ticks: List<Tick> = listOf(
        createMockTick(),
        createMockTick(startTimestamp = LocalDateTime.now().minusDays(2))
    ),
    tickUnit: TickUnit = TickUnit.DAY
): Chart {
    return Chart(
        id = id,
        tickerSymbol = tickerSymbol,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        ticks = ticks,
        tickUnit = tickUnit
    )
}

fun createMockTick(
    openPrice: Money = Money(50000.0),
    maxPrice: Money = Money(55000.0),
    minPrice: Money = Money(49500.0),
    closePrice: Money = Money(55000.0),
    transactionCount: Int = 1000,
    startTimestamp: LocalDateTime = LocalDateTime.now().minusDays(1),
    tradingVolume: Int = 10000,
    volumeWeightedAveragePrice: Money = Money(52000.0)
): Tick {
    return Tick(
        openPrice = openPrice,
        maxPrice = maxPrice,
        minPrice = minPrice,
        closePrice = closePrice,
        transactionCount = transactionCount,
        startTimestamp = startTimestamp,
        tradingVolume = tradingVolume,
        volumeWeightedAveragePrice = volumeWeightedAveragePrice
    )
}
