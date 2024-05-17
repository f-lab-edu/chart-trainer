package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.repository.ChartGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateNextTickUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository
) {
    suspend operator fun invoke(gameId: Long): Flow<Result<Unit>> =
        flow<Nothing> {
            val newChartGame = chartGameRepository.fetchChartGame(gameId = gameId).getNextTurn()

            if (newChartGame.isGameEnd) {
                throw ChartGameException.CanNotUpdateNextTickException(
                    message = "can't update next tick because game has been end"
                )
            }

            chartGameRepository.updateChartGame(chartGame = newChartGame)
        }.delegateEmptyResultFlow()
}
