package com.yessorae.domain.model

import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import org.junit.Assert.assertEquals
import org.junit.Test

class TradeTest {
    @Test
    fun profit_sell_calculation() {
        // 익절. 평단가 40,000원에 10개를 가지고 있다가 50,000원에 5개를 팔았을 때
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
        // 손절. 평단가 50,000원에 10개를 가지고 있다가 40,000원에 5개를 팔았을 때
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
        // 매수한 경우 손익은 수수료만큼 손해
        val trade = baseTestTrade.copy(
            type = TradeType.BUY,
            stockPrice = Money.of(50_000.0),
            commissionRate = 0.1,
            count = 100
        )

        assertEquals(Money.of(-5_000.0), trade.profit)
    }
}
