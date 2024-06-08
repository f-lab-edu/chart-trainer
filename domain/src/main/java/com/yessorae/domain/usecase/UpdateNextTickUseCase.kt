package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.User
import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateNextTickUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(gameId: Long): Flow<Result<Unit>> =
        flow<Nothing> {
            val oldChartGame = chartGameRepository.fetchChartGame(gameId = gameId)

            if (oldChartGame.isGameEnd) {
                throw ChartGameException.CanNotUpdateNextTickException(
                    message = "can't update next tick because game has been end"
                )
            }

            val newChartGame = oldChartGame.getNextTurn()

            if (newChartGame.isGameEnd) {
                val oldUser: User = userRepository.fetchUser()
                userRepository.updateUser(
                    oldUser.copyFrom(
                        profit = newChartGame.totalProfit.value,
                        rateOfProfit = newChartGame.rateOfProfit
                    )
                )
                chartGameRepository.clearLastChartGameId()
            }

            chartGameRepository.updateChartGame(chartGame = newChartGame)
        }.delegateEmptyResultFlow()
}
