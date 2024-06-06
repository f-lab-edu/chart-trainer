package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.SettingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TradeStockUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository,
    private val settingRepository: SettingRepository
) {
    operator fun invoke(param: Param): Flow<Result<Unit>> =
        flow<Nothing> {
            with(param) {
                val trade = Trade.new(
                    gameId = gameId,
                    ownedAverageStockPrice = ownedAverageStockPrice,
                    stockPrice = stockPrice,
                    count = count,
                    turn = turn,
                    type = type,
                    commissionRate = settingRepository.fetchCommissionRate()
                )

                chartGameRepository.updateChartGame(
                    chartGame = chartGameRepository.fetchChartGame(
                        gameId = gameId
                    ).copyFrom(
                        newTrade = trade
                    )
                )
            }
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
