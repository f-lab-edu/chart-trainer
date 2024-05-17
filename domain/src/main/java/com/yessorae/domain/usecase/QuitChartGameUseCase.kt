package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.repository.ChartGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuitChartGameUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository
) {
    suspend operator fun invoke(gameId: Long): Flow<Result<Unit>> =
        flow<Nothing> {
            val oldChartGame = chartGameRepository.fetchChartGame(gameId = gameId)
            chartGameRepository.updateChartGame(chartGame = oldChartGame.createFromQuit())
        }.delegateEmptyResultFlow()
}
