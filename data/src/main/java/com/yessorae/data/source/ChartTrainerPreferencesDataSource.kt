package com.yessorae.data.source

import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import kotlinx.coroutines.flow.Flow

interface ChartTrainerPreferencesDataSource {
    val userFlow: Flow<User>
    val commissionRateFlow: Flow<Double>
    val totalTurnFlow: Flow<Int>
    val tickUnitFlow: Flow<TickUnit>
    val lastChartGameIdFlow: Flow<Long?>

    suspend fun getUser(): User
    suspend fun getCommissionRate(): Double
    suspend fun getTotalTurn(): Int
    suspend fun getTickUnit(): TickUnit

    suspend fun updateUser(user: User)
    suspend fun updateCommissionRate(rate: Double)
    suspend fun updateTotalTurn(turn: Int)
    suspend fun updateTickUnit(tickUnit: TickUnit)
    suspend fun clearLastChartGameId()
    suspend fun updateLastChartGameId(gameId: Long)
}
