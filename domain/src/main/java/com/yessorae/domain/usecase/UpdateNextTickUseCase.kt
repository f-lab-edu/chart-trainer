package com.yessorae.domain.usecase

import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.repository.ChartGameRepository
import javax.inject.Inject

class UpdateNextTickUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository
) {
    suspend operator fun invoke(gameId: Long): Result<Unit> {
        val newChartGame = chartGameRepository.fetchChartGame(gameId = gameId).getNextTurn()

        if (newChartGame.isGameEnd) {
            return Result.failure(
                ChartGameException.CanNotUpdateNextTickException(
                    message = "can't update next tick because game has been end"
                )
            )
        }

        chartGameRepository.updateChartGame(chartGame = newChartGame)

        return Result.success(Unit)
    }
}
