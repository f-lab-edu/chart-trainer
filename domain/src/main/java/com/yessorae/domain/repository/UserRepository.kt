package com.yessorae.domain.repository

import com.yessorae.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun fetchUserAsFlow(): Flow<User>
    suspend fun fetchUser(): User
    suspend fun updateUser(user: User)

}

