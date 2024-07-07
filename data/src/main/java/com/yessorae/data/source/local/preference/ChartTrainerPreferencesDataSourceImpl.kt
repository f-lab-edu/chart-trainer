package com.yessorae.data.source.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yessorae.data.source.ChartTrainerPreferencesDataSource
import com.yessorae.domain.common.DefaultValues.DEFAULT_COMMISSION_RATE
import com.yessorae.domain.common.DefaultValues.DEFAULT_TOTAL_TURN
import com.yessorae.domain.common.DefaultValues.defaultTickUnit
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChartTrainerPreferencesDataSourceImpl @Inject constructor(
    private val appPreference: DataStore<Preferences>
) : ChartTrainerPreferencesDataSource {
    private val userKey = stringPreferencesKey("user")
    private val commissionRateKey = doublePreferencesKey("commission_rate")
    private val totalTurnKey = intPreferencesKey("total_turn")
    private val tickUnitKey = stringPreferencesKey("tick_unit")
    private val lastChartGameIdKey = longPreferencesKey("last_chart_game_id")

    private val data: Flow<Preferences> = appPreference.data

    override val userFlow: Flow<User> = data.map { preferences ->
        preferences[userKey]?.let { value ->
            // Data 레이어 전용 User 값-객체를 만들지 고민중
            Json.decodeFromString<User>(value)
        } ?: User.createInitialUser()
    }

    override val commissionRateFlow: Flow<Double> = data.map { preferences ->
        preferences[commissionRateKey] ?: DEFAULT_COMMISSION_RATE
    }

    override val totalTurnFlow: Flow<Int> = data.map { preferences ->
        preferences[totalTurnKey] ?: DEFAULT_TOTAL_TURN
    }

    override val tickUnitFlow: Flow<TickUnit> = data.map { preferences ->
        preferences[tickUnitKey]?.let { value ->
            TickUnit.valueOf(value)
        } ?: defaultTickUnit
    }

    override val lastChartGameIdFlow: Flow<Long?> = data.map { preferences ->
        preferences[lastChartGameIdKey]
    }

    override suspend fun getUser(): User = userFlow.firstOrNull() ?: User.createInitialUser()

    override suspend fun getCommissionRate(): Double = commissionRateFlow.firstOrNull() ?: DEFAULT_COMMISSION_RATE

    override suspend fun getTotalTurn(): Int = totalTurnFlow.firstOrNull() ?: DEFAULT_TOTAL_TURN

    override suspend fun getTickUnit(): TickUnit = tickUnitFlow.firstOrNull() ?: defaultTickUnit

    // Data 레이어 전용 User 값-객체를 만들지 고민중
    override suspend fun updateUser(user: User) {
        appPreference.edit { preferences ->
            preferences[userKey] = Json.encodeToString(user)
        }
    }

    override suspend fun updateCommissionRate(rate: Double) {
        appPreference.edit { preferences ->
            preferences[commissionRateKey] = rate
        }
    }

    override suspend fun updateTotalTurn(turn: Int) {
        appPreference.edit { preferences ->
            preferences[totalTurnKey] = turn
        }
    }

    override suspend fun updateTickUnit(tickUnit: TickUnit) {
        appPreference.edit { preferences ->
            preferences[tickUnitKey] = tickUnit.name
        }
    }

    override suspend fun clearLastChartGameId() {
        appPreference.edit { preferences ->
            preferences.remove(lastChartGameIdKey)
        }
    }

    override suspend fun updateLastChartGameId(gameId: Long) {
        appPreference.edit { preferences ->
            preferences[lastChartGameIdKey] = gameId
        }
    }
}
