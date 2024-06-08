package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.User
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuitChartGameUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(gameId: Long): Flow<Result<Unit>> =
        flow<Nothing> {
            val oldChartGame = chartGameRepository.fetchChartGame(gameId = gameId)
            chartGameRepository.updateChartGame(chartGame = oldChartGame.createFromQuit())

            chartGameRepository.clearLastChartGameId()

            // Quit 하면 패배 처리
            val oldUser: User = userRepository.fetchUser()
            userRepository.updateUser(
                oldUser.copyFrom(
                    profit = oldChartGame.totalProfit.value,
                    rateOfProfit = oldChartGame.rateOfProfit
                )
            )
        }.delegateEmptyResultFlow()
}
