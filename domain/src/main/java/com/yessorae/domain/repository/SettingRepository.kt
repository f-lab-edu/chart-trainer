package com.yessorae.domain.repository

import com.yessorae.domain.entity.tick.TickUnit
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun fetchCommissionRateAsFlow(): Flow<Double>
    suspend fun fetchCommissionRate(): Double
    suspend fun updateCommissionRate(rate: Double)

    fun fetchTotalTurnAsFlow(): Flow<Int>
    suspend fun fetchTotalTurn(): Int
    suspend fun updateTotalTurn(turn: Int)

    fun fetchTickUnitAsFlow(): Flow<TickUnit>
    suspend fun updateTickUnit(tickUnit: TickUnit)
}
