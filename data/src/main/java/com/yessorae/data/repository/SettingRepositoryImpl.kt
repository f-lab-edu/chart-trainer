package com.yessorae.data.repository

import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.SettingRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingRepositoryImpl @Inject constructor(
    private val appPreference: ChartTrainerPreferencesDataSource,
    @Dispatcher(ChartTrainerDispatcher.IO)
    private val dispatcher: CoroutineDispatcher
) : SettingRepository {
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
