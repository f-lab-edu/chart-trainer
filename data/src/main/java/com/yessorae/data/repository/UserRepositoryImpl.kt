package com.yessorae.data.repository

import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.domain.entity.User
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl @Inject constructor(
    private val chartTrainerPreferencesDataSource: ChartTrainerPreferencesDataSource
) : UserRepository {
    override fun fetchUserAsFlow(): Flow<User> {
        return chartTrainerPreferencesDataSource.userFlow
    }

    override suspend fun fetchUser(): User {
        return chartTrainerPreferencesDataSource.getUser()
    }

    override suspend fun updateUser(user: User) {
        chartTrainerPreferencesDataSource.updateUser(user = user)
    }
}
