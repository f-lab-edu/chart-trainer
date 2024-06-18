package com.yessorae.domain.entity

import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.value.Money

data class ChartGame(
    val id: Long = 0,
    // 차트 데이터
    val chart: Chart,
    // 거래 내역
    val trades: List<Trade>,
    // 현재 턴
    val currentTurn: Int,
    // 전체 턴
    val totalTurn: Int,
    // 게임 전 잔고
    val startBalance: Money,
    // 현재 잔고
    val currentBalance: Money,
    // 유저의 게임 강제 종료 여부
    val isQuit: Boolean
) {

    private val sortedTicks = chart.ticks.sortedBy { it.startTimestamp }

    private val lastVisibleIndex = (chart.ticks.size - 1) - (totalTurn - currentTurn)

    // 현재 턴의까지의 차트 데이터
    val visibleTicks: List<Tick> = if (chart.ticks.size <= lastVisibleIndex) {
        sortedTicks
    } else {
        sortedTicks.subList(0, lastVisibleIndex)
    }

    // 보유 주식 수량
    val ownedStockCount = trades.sumOf { trade ->
        if (trade.type.isBuy()) {
            trade.count
        } else {
            -trade.count
        }
    }

    // 보유 주식 총 가치
    private val ownedTotalStockPrice = trades.sumOf { trade ->
        if (trade.type.isBuy()) {
            trade.totalTradeMoney.value
        } else {
            -trade.totalTradeMoney.value
        }
    }

    // 현재 보유 주식 평단가
    val ownedAverageStockPrice = if (ownedStockCount != 0) {
        Money(ownedTotalStockPrice / ownedStockCount)
    } else {
        Money(0.0)
    }

    // 현재 종가
    val currentClosePrice: Money = (visibleTicks.lastOrNull()?.closePrice ?: Money(0.0))

    // 누적 수익
    val accumulatedTotalProfit: Money = trades.fold(Money(0.0)) { acc, trade ->
        acc + trade.profit
    } + if (ownedStockCount != 0) {
        currentClosePrice - ownedAverageStockPrice
    } else {
        Money(0.0)
    }

    // 누적 수익률
    val accumulatedRateOfProfit: Double = (accumulatedTotalProfit / startBalance).value

    // 현재 게임 진행률
    val currentGameProgress: Float = currentTurn / totalTurn.toFloat()

    // 게임 모든 턴을 끝까지 완료한 경우 true
    val isGameComplete: Boolean = currentTurn == totalTurn

    // 정상종료이든 강제종료이든 종료된 경우 true
    val isGameEnd: Boolean = isQuit || isGameComplete

    fun getNextTurn(): ChartGame {
        val nextTurn = currentTurn + 1
        return this.copy(
            currentTurn = nextTurn
        )
    }

    internal fun copyFrom(newChart: Chart): ChartGame {
        return copy(
            chart = newChart,
            currentTurn = START_TURN,
            trades = emptyList(),
            currentBalance = startBalance
        )
    }

    internal fun copyFrom(newTrade: Trade): ChartGame {
        return copy(
            trades = trades + newTrade,
            currentBalance = currentBalance - newTrade.totalTradeMoney
        )
    }

    internal fun createFromQuit(): ChartGame {
        return copy(
            isQuit = true
        )
    }

    companion object {
        private const val START_TURN = 1

        fun new(
            chart: Chart,
            totalTurn: Int,
            startBalance: Money
        ): ChartGame {
            return ChartGame(
                chart = chart,
                trades = emptyList(),
                currentTurn = START_TURN,
                totalTurn = totalTurn,
                startBalance = startBalance,
                currentBalance = startBalance,
                isQuit = false
            )
        }
    }
}
