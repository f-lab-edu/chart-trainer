package com.yessorae.data.repository

import com.yessorae.data.source.ChartTrainerPreferencesDataSource
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(
    private val appPreference: ChartTrainerPreferencesDataSource
) : UserRepository {
    override fun fetchUserAsFlow(): Flow<User> = appPreference.userFlow

    override suspend fun fetchUser(): User = appPreference.getUser()

    override suspend fun updateUser(user: User) = appPreference.updateUser(user = user)

    override fun fetchCommissionRateAsFlow(): Flow<Double> = appPreference.commissionRateFlow

    override suspend fun fetchCommissionRate(): Double = appPreference.getCommissionRate()

    override suspend fun updateCommissionRate(rate: Double) =
        appPreference.updateCommissionRate(rate)

    override fun fetchTotalTurnAsFlow(): Flow<Int> = appPreference.totalTurnFlow

    override suspend fun fetchTotalTurn(): Int = appPreference.getTotalTurn()

    override suspend fun updateTotalTurn(turn: Int) = appPreference.updateTotalTurn(turn)

    override fun fetchTickUnitAsFlow(): Flow<TickUnit> = appPreference.tickUnitFlow

    override suspend fun updateTickUnit(tickUnit: TickUnit) = appPreference.updateTickUnit(tickUnit)
}
