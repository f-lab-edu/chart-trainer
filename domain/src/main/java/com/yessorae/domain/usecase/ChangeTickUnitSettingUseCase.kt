package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateEmptyResultFlow
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.SettingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeTickUnitSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(tickUnit: TickUnit): Flow<Result<Unit>> =
        flow<Nothing> {
            settingRepository.updateTickUnit(tickUnit)
        }.delegateEmptyResultFlow()
}
