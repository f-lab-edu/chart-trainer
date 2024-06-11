package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SubscribeTotalTurnSettingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Result<Int>> =
        userRepository
            .fetchTotalTurnAsFlow()
            .delegateValueResultFlow()
}
