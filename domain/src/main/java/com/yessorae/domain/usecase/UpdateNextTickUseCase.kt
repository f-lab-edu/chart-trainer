package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.User
import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import com.yessorae.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateNextTickUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository,
    private val chartRepository: ChartRepository,
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

            val chart = chartRepository.fetchChart(gameId = gameId)
            val lastVisibleTickIndex = (chart.ticks.size - 1) -
                    (oldChartGame.totalTurn - (oldChartGame.currentTurn + 1))

            if (lastVisibleTickIndex < 0) {
                throw ChartGameException.CanNotCreateChartGame(
                    message = "can't change chart because new chart has not enough ticks"
                )
            }

            // getNextTurnResult 에 Chart 를 전달하고 인덱스 검사하는 부분을 getNextTurnResult 에 포함할지 고민중
            val newChartGame = oldChartGame.getNextTurnResult(
                closeStockPrice = chart.ticks[lastVisibleTickIndex].closePrice
            )

            if (newChartGame.isGameEnd) {
                val oldUser: User = userRepository.fetchUser()
                userRepository.updateUser(
                    oldUser.finishGame(
                        profit = newChartGame.accumulatedTotalProfit.value,
                        rateOfProfit = newChartGame.accumulatedRateOfProfit
                    )
                )
                chartGameRepository.clearLastChartGameId()
            }

            chartGameRepository.updateChartGame(chartGame = newChartGame)
        }.delegateEmptyResultFlow()
}
