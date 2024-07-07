package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.exception.ChartGameException.CanNotCreateChartGame
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import com.yessorae.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubscribeChartGameUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val chartRepository: ChartRepository,
    private val chartGameRepository: ChartGameRepository
) {
    suspend operator fun invoke(gameId: Long?): Flow<Result<SuccessData>> {
        if (gameId != null) {
            val chart = chartRepository.fetchChart(gameId = gameId)

            return chartGameRepository
                .fetchChartGameFlow(gameId = gameId)
                .map { chartGame ->
                    val lastVisibleTickIndex =
                        (chart.ticks.size - 1) - (chartGame.totalTurn - ChartGame.START_TURN)

                    assert(value = lastVisibleTickIndex >= 0)

                    SuccessData(
                        chartGame = chartGame,
                        visibleTicks = chart.ticks.subList(
                            0,
                            lastVisibleTickIndex
                        )
                    )
                }
                .delegateValueResultFlow()
        }

        val totalTurn = userRepository.fetchTotalTurn()

        val newChart = chartRepository.fetchNewChartRandomly(totalTurn = totalTurn)
        val initialLastVisibleTickIndex =
            (newChart.ticks.size - 1) - (totalTurn - ChartGame.START_TURN)
        if (initialLastVisibleTickIndex < 0) {
            throw CanNotCreateChartGame(
                message = "can't change chart because new chart has not enough ticks"
            )
        }
        val newGameId = chartGameRepository.createNewChartGame(
            chartGame = ChartGame.new(
                chartId = newChart.id,
                lastVisibleTickIndex = initialLastVisibleTickIndex,
                totalTurn = totalTurn,
                startBalance = userRepository.fetchUser().balance,
                closeStockPrice = newChart.ticks[initialLastVisibleTickIndex].closePrice
            )
        )
        chartGameRepository.updateLastChartGameId(gameId = newGameId)

        return chartGameRepository
            .fetchChartGameFlow(gameId = newGameId)
            .map { chartGame ->
                val chart = chartRepository.fetchChart(gameId = chartGame.id)
                SuccessData(
                    chartGame = chartGame,
                    visibleTicks = chart.ticks.subList(
                        fromIndex = 0,
                        toIndex = chartGame.lastVisibleTickIndex
                    )
                )
            }
            .delegateValueResultFlow()
    }

    data class SuccessData(
        val chartGame: ChartGame,
        val visibleTicks: List<Tick>
    )
}
