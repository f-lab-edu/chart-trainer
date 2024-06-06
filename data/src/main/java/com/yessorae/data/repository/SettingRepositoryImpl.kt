package com.yessorae.data.repository

import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.domain.repository.SettingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
}