package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.repository.SettingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeCommissionRateSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    operator fun invoke(rate: Double): Flow<Result<Unit>> =
        flow<Nothing> {
            settingRepository.updateCommissionRate(rate)
        }.delegateEmptyResultFlow()
}
