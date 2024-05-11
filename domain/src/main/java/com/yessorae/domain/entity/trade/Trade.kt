package com.yessorae.domain.entity.trade

import com.yessorae.domain.entity.value.Money

data class Trade(
    // 차트게임 아이디
    val gameId: Long,
    // 현재 가지고 있는 가격
    val ownedAverageStockPrice: Money,
    // 1 주당 가격
    val stockPrice: Money,
    // 거래량
    val count: Int,
    // 거래한 턴
    val turn: Int,
    // 매수/매도
    val type: TradeType,
    // 수수료
    val commission: Money,
    // 실현 손익
    val profit: Money,
    // 총 거래금
    val totalTradeMoney: Money,
    // TODO::LATER 세금
) {
    companion object {
        internal fun new(
            gameId: Long,
            ownedAverageStockPrice: Money,
            stockPrice: Money,
            count: Int,
            turn: Int,
            type: TradeType,
            commissionRate: Double,
        ): Trade {
            val totalTradeMoney: Money =
                calculateTotalTradeMoney(
                    stockPrice = stockPrice,
                    count = count,
                )
            val commission: Money =
                calculateCommission(
                    totalTradeMoney = totalTradeMoney,
                    commissionRate = commissionRate,
                )
            return Trade(
                gameId = gameId,
                ownedAverageStockPrice = ownedAverageStockPrice,
                stockPrice = stockPrice,
                count = count,
                turn = turn,
                type = type,
                totalTradeMoney = totalTradeMoney,
                commission = commission,
                profit =
                    calculateProfit(
                        stockPrice = stockPrice,
                        ownedAverageStockPrice = ownedAverageStockPrice,
                        count = count,
                        commission = commission,
                    ),
            )
        }

        private fun calculateTotalTradeMoney(
            stockPrice: Money,
            count: Int,
        ): Money {
            return stockPrice * count
        }

        private fun calculateCommission(
            totalTradeMoney: Money,
            commissionRate: Double,
        ): Money {
            return totalTradeMoney * commissionRate
        }

        private fun calculateProfit(
            stockPrice: Money,
            ownedAverageStockPrice: Money,
            count: Int,
            commission: Money,
        ): Money {
            return ((stockPrice - ownedAverageStockPrice) * count) - commission
        }
    }
}
