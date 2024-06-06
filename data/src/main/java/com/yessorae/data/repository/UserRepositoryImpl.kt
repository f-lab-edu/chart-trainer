package com.yessorae.data.repository

import com.yessorae.domain.entity.User
import com.yessorae.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun fetchUserAsFlow(): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}
