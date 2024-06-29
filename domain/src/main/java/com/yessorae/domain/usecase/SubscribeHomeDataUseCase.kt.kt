package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SubscribeHomeDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Result<Home>> =
        combine(
            userRepository.fetchUserAsFlow(),
            userRepository.fetchCommissionRateAsFlow(),
            userRepository.fetchTickUnitAsFlow(),
            userRepository.fetchTotalTurnAsFlow()
        ) { user,
            commissionRate,
            tickUnit,
            totalTurn ->

            Home(
                user = user,
                commissionRate = commissionRate,
                tickUnit = tickUnit,
                totalTurn = totalTurn
            )
        }.delegateValueResultFlow()

    data class Home(
        val user: User,
        val commissionRate: Double,
        val totalTurn: Int,
        val tickUnit: TickUnit
    )
}
