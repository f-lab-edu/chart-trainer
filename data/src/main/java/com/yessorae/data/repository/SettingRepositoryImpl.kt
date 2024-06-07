package com.yessorae.data.repository

import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.SettingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingRepositoryImpl @Inject constructor(
    private val appPreference: ChartTrainerPreferencesDataSource
) : SettingRepository {
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
