package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SubscribeHomeDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val chartGameRepository: ChartGameRepository
) {
    operator fun invoke(): Flow<Result<Home>> =
        combine(
            userRepository.fetchUserAsFlow(),
            userRepository.fetchCommissionRateAsFlow(),
            userRepository.fetchTickUnitAsFlow(),
            userRepository.fetchTotalTurnAsFlow(),
            chartGameRepository.fetchLastChartGameId().delegateValueResultFlow()
        ) { user,
            commissionRate,
            tickUnit,
            totalTurn,
            lastChartGameId ->

            Home(
                user = user,
                commissionRate = commissionRate,
                tickUnit = tickUnit,
                totalTurn = totalTurn,
                lastChartGameIdResult = lastChartGameId
            )
        }.delegateValueResultFlow()

    data class Home(
        val user: User,
        val commissionRate: Double,
        val totalTurn: Int,
        val tickUnit: TickUnit,
        val lastChartGameIdResult: Result<Long?>
    )
}
