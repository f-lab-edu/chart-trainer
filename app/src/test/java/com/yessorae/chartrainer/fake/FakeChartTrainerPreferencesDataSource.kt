package com.yessorae.chartrainer.fake

import com.yessorae.data.source.ChartTrainerPreferencesDataSource
import com.yessorae.domain.common.DefaultValues.DEFAULT_COMMISSION_RATE
import com.yessorae.domain.common.DefaultValues.DEFAULT_TOTAL_TURN
import com.yessorae.domain.common.DefaultValues.defaultTickUnit
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull

class FakeChartTrainerPreferencesDataSource : ChartTrainerPreferencesDataSource {
    private val _userFlow = MutableStateFlow(User.createInitialUser())
    private val _commissionRateFlow = MutableStateFlow(DEFAULT_COMMISSION_RATE)
    private val _totalTurnFlow = MutableStateFlow(DEFAULT_TOTAL_TURN)
    private val _tickUnitFlow = MutableStateFlow(defaultTickUnit)
    private val _lastChartGameIdFlow = MutableStateFlow<Long?>(null)

    override val userFlow: Flow<User> get() = _userFlow
    override val commissionRateFlow: Flow<Double> get() = _commissionRateFlow
    override val totalTurnFlow: Flow<Int> get() = _totalTurnFlow
    override val tickUnitFlow: Flow<TickUnit> get() = _tickUnitFlow
    override val lastChartGameIdFlow: Flow<Long?> get() = _lastChartGameIdFlow

    override suspend fun getUser(): User = _userFlow.firstOrNull() ?: User.createInitialUser()
    override suspend fun getCommissionRate(): Double =
        _commissionRateFlow.firstOrNull() ?: DEFAULT_COMMISSION_RATE
    override suspend fun getTotalTurn(): Int = _totalTurnFlow.firstOrNull() ?: DEFAULT_TOTAL_TURN
    override suspend fun getTickUnit(): TickUnit = _tickUnitFlow.firstOrNull() ?: defaultTickUnit

    override suspend fun updateUser(user: User) {
        _userFlow.value = user
    }

    override suspend fun updateCommissionRate(rate: Double) {
        _commissionRateFlow.value = rate
    }

    override suspend fun updateTotalTurn(turn: Int) {
        _totalTurnFlow.value = turn
    }

    override suspend fun updateTickUnit(tickUnit: TickUnit) {
        _tickUnitFlow.value = tickUnit
    }

    override suspend fun clearLastChartGameId() {
        _lastChartGameIdFlow.value = null
    }

    override suspend fun updateLastChartGameId(gameId: Long) {
        _lastChartGameIdFlow.value = gameId
    }
}
