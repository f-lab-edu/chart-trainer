package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeCommissionRateSettingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(rate: Double): Flow<Result<Unit>> =
        flow<Nothing> {
            userRepository.updateCommissionRate(rate)
        }.delegateEmptyResultFlow()
}
