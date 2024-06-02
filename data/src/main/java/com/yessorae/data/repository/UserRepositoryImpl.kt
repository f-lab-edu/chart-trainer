package com.yessorae.data.repository

import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl @Inject constructor(
    private val appPreference: ChartTrainerPreferencesDataSource,
    @Dispatcher(ChartTrainerDispatcher.IO)
    private val dispatcher: CoroutineDispatcher
) : UserRepository {
    override suspend fun fetchCommissionRateConfig(): Double =
        withContext(dispatcher) {
            appPreference.getCommissionRate()
        }

    override suspend fun fetchTotalTurnConfig(): Int =
        withContext(dispatcher) {
            appPreference.getTotalTurn()
        }

    override suspend fun fetchCurrentBalance(): Money =
        withContext(dispatcher) {
            appPreference.getCurrentBalance()
        }
}
