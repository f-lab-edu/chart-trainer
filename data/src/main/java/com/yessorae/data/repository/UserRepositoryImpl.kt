package com.yessorae.data.repository

import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appPreference: ChartTrainerPreferencesDataSource
) : UserRepository {
    override suspend fun fetchCommissionRateConfig(): Double {
        return appPreference.getCommissionRate()
    }

    override suspend fun fetchTotalTurnConfig(): Int {
        return appPreference.getTotalTurn()
    }

    override suspend fun fetchCurrentBalance(): Money {
        return appPreference.getCurrentBalance()
    }
}
