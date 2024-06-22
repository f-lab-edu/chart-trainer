package com.yessorae.domain

import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.model.baseTestChartGame
import com.yessorae.domain.model.baseTestTrade
import org.junit.Assert.assertEquals
import org.junit.Test

class ChartGameTest {
    @Test
    fun profit_sell_chart_game_result() {
        // 익절. 평단가 40,000원에 10개를 가지고 있다가 50,000원에 5개를 팔았을 때
        val trade: Trade = baseTestTrade.copy(
            type = TradeType.SELL,
            ownedAverageStockPrice = Money.of(40_000.0),
            ownedStockCount = 10,
            stockPrice = Money.of(50_000.0),
            count = 5,
            commissionRate = 0.1
        )
        val sut: ChartGame = baseTestChartGame.copy(
            // 500만원 있는 사람이
            startBalance = Money.of(5_000_000.0),
            // 과거 4만원짜리 주식 10주 사서 현재 잔액은 460만원이고
            totalStockCount = 10,
            currentBalance = Money.of(4_600_000.0),
            // 평단가는 4만원
            averageStockPrice = Money.of(40_000.0),
            // 현재가격은 5만원
            closeStockPrice = Money.of(50_000.0),
            // 과거 4만원짜리 주식 10주 사서 발생한 수수료 손실금이 4백원인 상태
            accumulatedTotalProfit = Money.of(-400.0)
        )

        val result: ChartGame = sut.getTradeResult(newTrade = trade)

        assertEquals(
            sut.copy(
                currentBalance = Money.of(4_849_750.0),
                totalStockCount = 5,
                averageStockPrice = Money.of(40_000.0),
                accumulatedTotalProfit = Money.of(49_350.0)
            ),
            result
        )
    }

    @Test
    fun stop_loss_chart_game_result() {
        // 손절. 평단가 50,000원에 10개를 가지고 있다가 40,000원에 5개를 팔았을 때
        val trade: Trade = baseTestTrade.copy(
            type = TradeType.SELL,
            ownedAverageStockPrice = Money.of(50_000.0),
            ownedStockCount = 10,
            stockPrice = Money.of(40_000.0),
            count = 5,
            commissionRate = 0.1
        )
        val sut: ChartGame = baseTestChartGame.copy(
            // 500만원 있는 사람이
            startBalance = Money.of(5_000_000.0),
            // 5만원짜리 주식 10주 사서 현재 잔액은 450만원이고 평단가는 5만원 현재가격은 4만원
            totalStockCount = 10,
            currentBalance = Money.of(4_500_000.0),
            averageStockPrice = Money.of(50_000.0),
            closeStockPrice = Money.of(40_000.0),
            // 5만원짜리 주식 10주 사서 발생한 수수료 손실금
            accumulatedTotalProfit = Money.of(-500.0)
        )

        val result: ChartGame = sut.getTradeResult(newTrade = trade)

        assertEquals(
            sut.copy(
                currentBalance = Money.of(4_699_800.0),
                totalStockCount = 5,
                averageStockPrice = Money.of(50_000.0),
                accumulatedTotalProfit = Money.of(-50_700.0)
            ),
            result
        )
    }

    @Test
    fun buy_chart_game_result() {
        // 5만원에 100주 매수.
        val trade: Trade = baseTestTrade.copy(
            type = TradeType.BUY,
            stockPrice = Money.of(50_000.0),
            commissionRate = 0.1,
            count = 10
        )
        val sut: ChartGame = baseTestChartGame.copy(
            // 500만원 있는 사람이
            startBalance = Money.of(5_000_000.0),
            // 과거 4만원짜리 주식 10주 사서 현재 잔액은 460만원이고
            totalStockCount = 10,
            currentBalance = Money.of(4_600_000.0),
            // 평단가는 4만원
            averageStockPrice = Money.of(40_000.0),
            // 현재가격은 5만원
            closeStockPrice = Money.of(50_000.0),
            // 과거 4만원짜리 주식 10주 사서 발생한 수수료 손실금이 400원인 상태
            accumulatedTotalProfit = Money.of(-400.0)
        )

        val result: ChartGame = sut.getTradeResult(newTrade = trade)


        assertEquals(
            sut.copy(
                currentBalance = Money.of(4_099_500.0),
                totalStockCount = 20,
                averageStockPrice = Money.of(45_000.0),
                accumulatedTotalProfit = Money.of(-900.0)
            ),
            result
        )
    }
}
