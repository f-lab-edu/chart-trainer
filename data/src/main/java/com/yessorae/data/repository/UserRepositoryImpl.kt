package com.yessorae.data.repository

import com.yessorae.domain.entity.User
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun fetchUserAsFlow(): Flow<User> {
        // TODO::LATER CT-5-2 에서 구현 필요, 기존 기능 실행은 되어야해서 임시조치
        return flow { }
    }

    override suspend fun fetchUser(): User {
        // TODO::LATER CT-5-2 에서 구현 필요, 기존 기능 실행은 되어야 해서 임시조치
        return User.createInitialUser()
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}
