package com.yessorae.domain

import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.model.baseTestChartGame
import com.yessorae.domain.model.baseTestTrade
import org.junit.Assert
import org.junit.Test

class ChartGameTest {
//    @Test
//    fun profit_sell_chart_game_result() {
//        // 익절. 평단가 40,000원에 10개를 가지고 있다가 50,000원에 5개를 팔았을 때
//        val trade = baseTestTrade.copy(
//            type = TradeType.SELL,
//            ownedAverageStockPrice = Money.of(40_000.0),
//            ownedStockCount = 10,
//            stockPrice = Money.of(50_000.0),
//            count = 5,
//            commissionRate = 0.1
//        )
//    }

    @Test
    fun stop_loss_chart_game_result() {
        // 손절. 평단가 50,000원에 10개를 가지고 있다가 40,000원에 5개를 팔았을 때
        val trade = baseTestTrade.copy(
            type = TradeType.SELL,
            ownedAverageStockPrice = Money.of(50_000.0),
            ownedStockCount = 10,
            stockPrice = Money.of(40_000.0),
            count = 5,
            commissionRate = 0.1
        )
        val sut = baseTestChartGame.copy(
            // 500만원 있는 사람이
            startBalance = Money.of(5_000_000.0),
            // 5만원짜리 주식 10주 사서 현재 잔액은 450만원이고 평단가는 5만원 현재가격은 4만원
            totalStockCount = 10,
            currentBalance = Money.of(4_500_000.0),
            averageStockPrice = Money.of(50_000.0),
            closeStockPrice = Money.of(40_000.0),
            // 5만원짜리 주식 10주 사서 발생한 수수료 손실금
            accumulatedTotalProfit = Money.of(-5_000.0)
        )

        val chartGameAfterTrade = sut.getTradeResult(newTrade = trade)

        Assert.assertEquals(
            sut.copy(
                currentBalance = Money.of(4_699_800.0),
                totalStockCount = 5,
                averageStockPrice = Money.of(50_000.0),
                // 25 만원이던 총 금액이 20만원에 팔렸고 200원 수수료 있으니 50200 원 손해?
                accumulatedTotalProfit = Money.of(-55_200.0)
            ),
            chartGameAfterTrade
        )
    }


//    @Test
//    fun buy_chart_game_result() {
//        // 매수한 경우 손익은 수수료만큼 손해
//        val trade = baseTestTrade.copy(
//            type = TradeType.BUY,
//            stockPrice = Money.of(50_000.0),
//            commissionRate = 0.1,
//            count = 100
//        )
//    }
}
