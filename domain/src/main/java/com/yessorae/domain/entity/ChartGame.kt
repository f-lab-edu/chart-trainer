package com.yessorae.domain.entity

import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.value.Money

data class ChartGame(
    val id: Long = 0,
    // 게임에 종속된 차트의 id
    val chartId: Long,
    // 현재 턴
    val currentTurn: Int,
    // 전체 턴
    val totalTurn: Int,
    // 게임 전 잔고
    val startBalance: Money,
    // 현재 잔고
    val currentBalance: Money,
    // 현재 종가
    val closeStockPrice: Money,
    // 유저의 게임 강제 종료 여부
    val isQuit: Boolean,
    // 현재 보유 주식 수량
    val totalStockCount: Int,
    // 현재 보유 주식 평단가
    val averageStockPrice: Money,
    // 누적 실현 손익
    val accumulatedTotalProfit: Money
) {
    // 현재 보유 주식 가격의 총합
    val totalStockPrice: Money = closeStockPrice * totalStockCount

    // 누적 수익률
    val accumulatedRateOfProfit: Double = (accumulatedTotalProfit / startBalance).value

    // 현재 게임 진행률
    val currentGameProgress: Float = currentTurn / totalTurn.toFloat()

    // 게임 모든 턴을 끝까지 완료한 경우 true
    val isGameComplete: Boolean = currentTurn == totalTurn

    // 정상종료이든 강제종료이든 종료된 경우 true
    val isGameEnd: Boolean = isQuit || isGameComplete

    internal fun getNextTurnResult(closeStockPrice: Money): ChartGame {
        val nextTurn = currentTurn + 1
        return this.copy(
            currentTurn = nextTurn,
            closeStockPrice = closeStockPrice
        )
    }

    internal fun getTradeResult(newTrade: Trade): ChartGame {
        val newTotalStockCount: Int = totalStockCount + if (newTrade.type.isBuy()) {
            newTrade.count
        } else {
            -newTrade.count
        }

        return copy(
            currentBalance = currentBalance + Money.of(
                if (newTrade.type.isBuy()) {
                    -(newTrade.totalTradeMoney + newTrade.commission).value
                } else {
                    (newTrade.totalTradeMoney - newTrade.commission).value
                }
            ),
            totalStockCount = newTotalStockCount,
            averageStockPrice = if (newTrade.type.isBuy()) {
                (averageStockPrice * totalStockCount + newTrade.totalTradeMoney) / newTotalStockCount
            } else {
                averageStockPrice
            },
            accumulatedTotalProfit = accumulatedTotalProfit + newTrade.profit
        )
    }

    internal fun getChartChangeResult(closeStockPrice: Money): ChartGame {
        return copy(
            currentTurn = START_TURN,
            currentBalance = startBalance,
            closeStockPrice = closeStockPrice,
            totalStockCount = 0,
            averageStockPrice = Money.ZERO,
            accumulatedTotalProfit = Money.ZERO
        )
    }

    internal fun getQuitResult(): ChartGame {
        return copy(
            isQuit = true
        )
    }

    companion object {
        const val START_TURN = 1

        fun new(
            chartId: Long,
            totalTurn: Int,
            startBalance: Money,
            closeStockPrice: Money
        ): ChartGame {
            return ChartGame(
                chartId = chartId,
                currentTurn = START_TURN,
                totalTurn = totalTurn,
                startBalance = startBalance,
                currentBalance = startBalance,
                closeStockPrice = closeStockPrice,
                isQuit = false,
                totalStockCount = 0,
                averageStockPrice = Money.ZERO,
                accumulatedTotalProfit = Money.ZERO
            )
        }
    }
}
