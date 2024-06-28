package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.TradeRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubscribeTradeHistoryUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository,
    private val tradeRepository: TradeRepository
) {
    operator fun invoke(gameId: Long): Flow<Result<SuccessData>> {
        return chartGameRepository
            .fetchChartGameFlow(gameId = gameId)
            .map { chartGame ->
                SuccessData(
                    chartGame = chartGame,
                    trades = tradeRepository.fetchTrades(gameId = gameId)
                )
            }
            .delegateValueResultFlow()
    }

    data class SuccessData(
        val chartGame: ChartGame,
        val trades: List<Trade>
    )
}
