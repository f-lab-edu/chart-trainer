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
    // 수수료율
    val commissionRate: Double,
    // TODO::LATER 세금
) {
    // 총 거래금
    val totalTradeMoney: Money = stockPrice * count

    // 수수료
    val commission: Money = totalTradeMoney * commissionRate

    // 실현 손익
    val profit: Money = ((stockPrice - ownedAverageStockPrice) * count) - commission

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
            return Trade(
                gameId = gameId,
                ownedAverageStockPrice = ownedAverageStockPrice,
                stockPrice = stockPrice,
                count = count,
                turn = turn,
                type = type,
                commissionRate = commissionRate,
            )
        }
    }
}
