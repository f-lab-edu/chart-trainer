package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.TradeRepository
import com.yessorae.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TradeStockUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository,
    private val tradeRepository: TradeRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(param: Param): Flow<Result<Unit>> =
        flow<Nothing> {
            with(param) {
                val trade = Trade.new(
                    gameId = gameId,
                    ownedStockCount = ownedStockCount,
                    ownedAverageStockPrice = ownedAverageStockPrice,
                    stockPrice = stockPrice,
                    count = count,
                    turn = turn,
                    type = type,
                    commissionRate = userRepository.fetchCommissionRate()
                )

                tradeRepository.createTrade(trade = trade)

                val oldChartGame = chartGameRepository.fetchChartGame(gameId = gameId)
                val newChartGame = oldChartGame.getTradeResult(newTrade = trade)
                chartGameRepository.updateChartGame(chartGame = newChartGame)
            }
        }.delegateEmptyResultFlow()

    data class Param(
        val gameId: Long,
        val ownedStockCount: Int,
        val ownedAverageStockPrice: Money,
        val stockPrice: Money,
        val count: Int,
        val turn: Int,
        val type: TradeType
    )
}
