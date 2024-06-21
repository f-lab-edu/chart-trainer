package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.User
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.TradeRepository
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
            chartGameRepository.updateChartGame(
                chartGame = chartGameRepository.fetchChartGame(gameId = gameId).getQuitResult()
            )
            chartGameRepository.clearLastChartGameId()
            userRepository.updateUser(user = userRepository.fetchUser().quiteGame())
        }.delegateEmptyResultFlow()
}
