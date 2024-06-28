package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeChartUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val chartRepository: ChartRepository,
    private val chartGameRepository: ChartGameRepository
) {
    operator fun invoke(gameId: Long): Flow<Result<Unit>> =
        flow<Nothing> {
            val oldChartGame = chartGameRepository.fetchChartGame(gameId = gameId)

            if (oldChartGame.isGameEnd) {
                throw ChartGameException.CanNotChangeChartException(
                    message = "can't change chart because game has been end"
                )
            }

            val totalTurn = userRepository.fetchTotalTurn()

            val newChart = chartRepository.fetchNewChartRandomly(totalTurn = totalTurn)

            if (newChart.ticks.size < totalTurn) {
                throw ChartGameException.CanNotChangeChartException(
                    message = "can't change chart because new chart has not enough ticks"
                )
            }
            val closeStockPrice = newChart.ticks[totalTurn - 1].closePrice

            val newChartGame = oldChartGame.getChartChangeResult(closeStockPrice = closeStockPrice)

            chartGameRepository.updateChartGame(
                chartGame = newChartGame
            )
        }.delegateEmptyResultFlow()
}
