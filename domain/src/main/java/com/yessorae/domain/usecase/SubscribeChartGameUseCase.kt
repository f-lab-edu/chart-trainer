package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SubscribeChartGameUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val chartRepository: ChartRepository,
    private val chartGameRepository: ChartGameRepository
) {
    suspend operator fun invoke(gameId: Long?): Flow<Result<ChartGame>> {
        if (gameId == null) {
            val totalTurn = userRepository.fetchTotalTurn()

            val newGameId = chartGameRepository.createNewChartGame(
                chartGame = ChartGame.new(
                    chart = chartRepository.fetchNewChartRandomly(totalTurn = totalTurn),
                    totalTurn = totalTurn,
                    startBalance = userRepository.fetchUser().balance
                )
            )

            chartGameRepository.updateLastChartGameId(gameId = newGameId)

            return chartGameRepository
                .fetchChartFlow(gameId = newGameId)
                .delegateValueResultFlow()
        }

        return chartGameRepository
            .fetchChartFlow(gameId = gameId)
            .delegateValueResultFlow()
    }
}
