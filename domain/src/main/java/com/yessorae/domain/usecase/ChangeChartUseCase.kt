package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeChartUseCase @Inject constructor(
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

            val totalTurn = oldChartGame.totalTurn

            val newChart = chartRepository.fetchNewChartRandomly(totalTurn = totalTurn)
            val lastVisibleTickIndex =
                (newChart.ticks.size - 1) - (totalTurn - ChartGame.START_TURN)
            if (lastVisibleTickIndex < 0) {
                throw ChartGameException.CanNotChangeChartException(
                    message = "can't change chart because new chart has not enough ticks"
                )
            }
            val closeStockPrice = newChart.ticks[lastVisibleTickIndex].closePrice

            val newChartGame = oldChartGame.getChartChangeResult(
                chartId = newChart.id,
                lastVisibleTickIndex = lastVisibleTickIndex,
                closeStockPrice = closeStockPrice
            )

            chartGameRepository.updateChartGame(
                chartGame = newChartGame
            )
        }.delegateEmptyResultFlow()
}
