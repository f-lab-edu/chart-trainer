package com.yessorae.domain.usecase

import com.yessorae.domain.repository.ChartGameRepository
import javax.inject.Inject

class UpdateNextBarUseCase
    @Inject
    constructor(
        private val chartGameRepository: ChartGameRepository,
    ) {
        suspend operator fun invoke(gameId: Long) {
            chartGameRepository.updateChartGame(
                chartGame = chartGameRepository.fetchChartGame(gameId = gameId).getNextTurn(),
            )
        }
    }
