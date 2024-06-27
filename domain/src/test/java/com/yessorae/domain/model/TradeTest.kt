package com.yessorae.domain.model

import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.asMoney
import org.junit.Assert.assertEquals
import org.junit.Test

class TradeTest {
    @Test
    fun profit_with_selling_part_of_owned_stock_at_higher_price() {
        val trade = createTestTrade(
            type = TradeType.SELL,
            ownedAverageStockPrice = 40_000.asMoney(),
            ownedStockCount = 10,
            stockPrice = 50_000.asMoney(),
            count = 5,
            commissionRate = 0.1
        )

        assertEquals(250_000.asMoney(), trade.totalTradeMoney)
        assertEquals(250.asMoney(), trade.commission)
        assertEquals(49_750.asMoney(), trade.profit)
    }

    @Test
    fun stop_loss_with_selling_part_of_owned_stock_at_higher_price() {
        val trade = createTestTrade(
            type = TradeType.SELL,
            ownedAverageStockPrice = 50_000.asMoney(),
            ownedStockCount = 10,
            stockPrice = 40_000.asMoney(),
            count = 5,
            commissionRate = 0.1
        )

        assertEquals(200_000.asMoney(), trade.totalTradeMoney)
        assertEquals(200.asMoney(), trade.commission)
        assertEquals((-50_200.0).asMoney(), trade.profit)
    }

    @Test
    fun loss_with_buying_commission_rate() {
        val trade = createTestTrade(
            type = TradeType.BUY,
            stockPrice = 50_000.asMoney(),
            commissionRate = 0.1,
            count = 100
        )

        assertEquals((-5_000).asMoney(), trade.profit)
    }
}
