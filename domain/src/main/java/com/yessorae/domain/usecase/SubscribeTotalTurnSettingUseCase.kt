package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.repository.SettingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SubscribeTotalTurnSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    operator fun invoke(): Flow<Result<Int>> =
        settingRepository
            .fetchTotalTurnAsFlow()
            .delegateValueResultFlow()
}
