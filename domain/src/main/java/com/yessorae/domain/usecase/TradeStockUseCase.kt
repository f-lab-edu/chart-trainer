package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TradeStockUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(param: Param): Flow<Result<Unit>> =
        flow<Nothing> {
            val trade = Trade.new(
                gameId = param.gameId,
                ownedAverageStockPrice = param.ownedAverageStockPrice,
                stockPrice = param.stockPrice,
                count = param.count,
                turn = param.turn,
                type = param.type,
                commissionRate = userRepository.fetchCommissionRateConfig()
            )

            chartGameRepository.updateChartGame(
                chartGame = chartGameRepository.fetchChartGame(
                    gameId = param.gameId
                ).copyFrom(
                    newTrade = trade
                )
            )
        }.delegateEmptyResultFlow()

    data class Param(
        val gameId: Long,
        val ownedAverageStockPrice: Money,
        val stockPrice: Money,
        val count: Int,
        val turn: Int,
        val type: TradeType
    )
}
