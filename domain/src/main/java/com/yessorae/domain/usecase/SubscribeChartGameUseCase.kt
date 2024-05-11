package com.yessorae.domain.usecase

import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import com.yessorae.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// TODO::NOW 새로 만드는 역할까지 하는 게 괜찮을지? 일단 기술과 관련없는 기획자/유저입장에서 이름짓고 로직 작성중
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
