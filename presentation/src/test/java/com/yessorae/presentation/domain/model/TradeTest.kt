package com.yessorae.presentation.domain.model

import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import org.junit.Assert.assertEquals
import org.junit.Test

class TradeTest {
    @Test
    fun `총 거래금액 계산`() {
        // Arrange
        val trade = createMockTrade()

        // Act
        val totalTradeMoney = trade.totalTradeMoney

        // Assert
        assertEquals(Money(5500000.0), totalTradeMoney)
    }

    @Test
    fun `수수료 계산`() {
        // Arrange
        val trade = createMockTrade()

        // Act
        val commission = trade.commission

        // Assert
        assertEquals(Money(11000.0), commission)
    }

    @Test
    fun `매수일 때 이익 계산`() {
        // Arrange
        val trade = createMockTrade(type = TradeType.BUY)

        // Act
        val profit = trade.profit

        // Assert
        assertEquals(Money(-11000.0), profit)
    }

    @Test
    fun `매도일 때 이익 계산`() {
        // Arrange
        val trade = createMockTrade(type = TradeType.SELL)

        // Act
        val profit = trade.profit

        // Assert
        assertEquals(Money(489000.0), profit)
    }

    @Test
    fun `수량이 0일 때`() {
        // Arrange
        val trade = createMockTrade(count = 0, type = TradeType.SELL)

        // Act
        val profit = trade.profit

        // Assert
        assertEquals(Money(0.0), profit)
    }

    @Test
    fun `매수와 매도 가격이 동일할 때`() {
        // Arrange
        val trade = createMockTrade(
            ownedAverageStockPrice = Money(50000.0),
            stockPrice = Money(50000.0),
            type = TradeType.SELL
        )

        // Act
        val profit = trade.profit

        // Assert
        assertEquals(Money(-10000.0), profit) // 수수료가 차감된 손익
    }

    @Test
    fun `수수료율이 0일 때`() {
        // Arrange
        val trade = createMockTrade(commissionRate = 0.0, type = TradeType.SELL)

        // Act & Assert
        assertEquals(Money(500_000.0), trade.profit)
    }
}
