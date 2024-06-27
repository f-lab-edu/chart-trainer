package com.yessorae.domain

import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.model.createTestTrade
import org.junit.Assert.assertEquals
import org.junit.Test

class TradeTest {
    @Test
    fun profit_with_selling_part_of_owned_stock_at_higher_price() {
        val trade = createTestTrade(
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
    fun stop_loss_with_selling_part_of_owned_stock_at_higher_price() {
        val trade = createTestTrade(
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
    fun loss_with_buying_commission_rate() {
        val trade = createTestTrade(
            type = TradeType.BUY,
            stockPrice = Money.of(50_000.0),
            commissionRate = 0.1,
            count = 100
        )

        assertEquals(Money.of(-5_000.0), trade.profit)
    }
}
