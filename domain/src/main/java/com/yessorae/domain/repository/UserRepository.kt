package com.yessorae.domain.repository

import com.yessorae.domain.entity.value.Money

interface UserRepository {
    suspend fun fetchCommissionRateConfig(): Double

    suspend fun fetchTotalTurnConfig(): Int

    suspend fun fetchCurrentBalance(): Money
}
