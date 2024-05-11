package com.yessorae.domain.usecase

import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import com.yessorae.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeChartGameUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val chartRepository: ChartRepository,
        private val chartGameRepository: ChartGameRepository,
    ) {
        suspend operator fun invoke(gameId: Long?): Flow<ChartGame> {
            if (gameId == null) {
                val newGameId =
                    chartGameRepository.saveChartGame(
                        chartGame =
                            ChartGame.new(
                                chart = chartRepository.fetchNewChartRandomly(),
                                totalTurn = userRepository.fetchTotalTurnConfig(),
                                startBalance = userRepository.fetchCurrentBalance(),
                            ),
                    )
                return chartGameRepository.fetchChartFlowStream(gameId = newGameId)
            }

            return chartGameRepository.fetchChartFlowStream(gameId = gameId)
        }
    }
