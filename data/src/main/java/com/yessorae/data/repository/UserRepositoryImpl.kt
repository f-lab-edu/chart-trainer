package com.yessorae.data.repository

import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UserRepositoryImpl @Inject constructor(
    private val appPreference: ChartTrainerPreferencesDataSource,
    @Dispatcher(ChartTrainerDispatcher.IO)
    private val dispatcher: CoroutineDispatcher
) : UserRepository {
    override fun fetchUserAsFlow(): Flow<User> {
        // TODO::LATER CT-5-2 에서 구현 필요, 기존 기능 실행은 되어야해서 임시조치
        return flow { }
    }

    override suspend fun fetchUser(): User {
        // TODO::LATER CT-5-2 에서 구현 필요, 기존 기능 실행은 되어야 해서 임시조치
        return User.createInitialUser()
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun fetchCommissionRateAsFlow(): Flow<Double> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCommissionRate(): Double =
        withContext(dispatcher) {
            appPreference.getCommissionRate()
        }

    override suspend fun updateCommissionRate(rate: Double) {
        TODO("Not yet implemented")
    }

    override fun fetchTotalTurnAsFlow(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchTotalTurn(): Int =
        withContext(dispatcher) {
            appPreference.getTotalTurn()
        }

    override suspend fun updateTotalTurn(turn: Int) {
        TODO("Not yet implemented")
    }

    override fun fetchTickUnitAsFlow(): Flow<TickUnit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchTickUnit(): TickUnit {
        TODO("Not yet implemented")
    }

    override suspend fun updateTickUnit(tickUnit: TickUnit) {
        TODO("Not yet implemented")
    }
}
