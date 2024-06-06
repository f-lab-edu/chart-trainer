package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.entity.User
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SubscribeUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Result<User>> =
        userRepository
            .fetchUserAsFlow()
            .delegateValueResultFlow()
}
