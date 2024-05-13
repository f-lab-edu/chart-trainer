package com.yessorae.domain.usecase

import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.TradeRepository
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject

class TradeStockUseCase
@Inject
constructor(
    private val chartGameRepository: ChartGameRepository,
    private val tradeRepository: TradeRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        gameId: Long,
        ownedAverageStockPrice: Money,
        stockPrice: Money,
        count: Int,
        turn: Int,
        type: TradeType
    ) {
        val trade =
            Trade.new(
                gameId = gameId,
                ownedAverageStockPrice = ownedAverageStockPrice,
                stockPrice = stockPrice,
                count = count,
                turn = turn,
                type = type,
                commissionRate = userRepository.fetchCommissionRateConfig()
            )

        tradeRepository.saveTradeHistory(trade = trade)

        chartGameRepository.updateChartGame(
            chartGame =
            chartGameRepository.fetchChartGame(
                gameId = gameId
            ).copyFrom(
                newTrade = trade
            )
        )
    }
}
