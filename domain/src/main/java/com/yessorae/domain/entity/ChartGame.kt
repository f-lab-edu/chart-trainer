package com.yessorae.domain.entity

import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.value.Money

data class ChartGame(
    val id: Int,
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
    // 게임 종료 여부
    val isGameEnd: Boolean,
) {
    val totalProfit: Money by lazy {
        currentBalance - startBalance
    }

    val rateOfProfit: Double by lazy {
        (totalProfit / startBalance).value * 100
    }

    val tradeCount: Int by lazy {
        trades.size
    }

    val totalCommission: Money by lazy {
        val totalValue = trades.sumOf { trade -> trade.commission.value }
        Money(totalValue)
    }

    val visibleTicks: List<Tick> by lazy {
        chart.ticks
            .sortedBy { it.startTimestamp }
            .subList(0, chart.ticks.size - totalTurn + currentTurn - 1)
    }

    internal fun getNextTurn(): ChartGame {
        return this.copy(
            currentTurn = currentTurn + 1,
        )
    }

    internal fun createFromNewChart(newChart: Chart): ChartGame {
        return copy(
            chart = newChart,
            currentTurn = START_TURN,
            trades = emptyList(),
            currentBalance = startBalance,
        )
    }

    internal fun createFromNewTrade(newTrade: Trade): ChartGame {
        return copy(
            trades = trades + newTrade,
            currentBalance = currentBalance + newTrade.profit,
        )
    }

    companion object {
        private const val START_TURN = 1

        fun new(
            chart: Chart,
            totalTurn: Int,
            startBalance: Money,
        ): ChartGame {
            return ChartGame(
                id = 0,
                chart = chart,
                trades = emptyList(),
                currentTurn = START_TURN,
                totalTurn = totalTurn,
                startBalance = startBalance,
                currentBalance = startBalance,
                isGameEnd = false,
            )
        }
    }
}
