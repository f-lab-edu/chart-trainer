package com.yessorae.domain.usecase

import com.yessorae.domain.repository.ChartGameRepository
import javax.inject.Inject

class QuitChartGameUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository
) {
    suspend operator fun invoke(gameId: Long) {
        val oldChartGame = chartGameRepository.fetchChartGame(gameId = gameId)
        chartGameRepository.updateChartGame(chartGame = oldChartGame.createFromQuit())
    }
}
