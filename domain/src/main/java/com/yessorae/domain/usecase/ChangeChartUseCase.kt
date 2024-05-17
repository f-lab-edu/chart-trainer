package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChangeChartUseCase @Inject constructor(
    private val chartRepository: ChartRepository,
    private val chartGameRepository: ChartGameRepository
) {
    suspend operator fun invoke(gameId: Long): Flow<Result<Unit>> =
        flow<Nothing> {
            val oldChartGame = chartGameRepository.fetchChartGame(gameId = gameId)

            if (oldChartGame.isGameEnd) {
                throw ChartGameException.CanNotChangeChartException(
                    message = "can't change chart because game has been end"
                )
            }

            chartGameRepository.updateChartGame(
                chartGame = oldChartGame.copyFrom(
                    newChart = chartRepository.fetchNewChartRandomly()
                )
            )
        }.delegateEmptyResultFlow()
}
