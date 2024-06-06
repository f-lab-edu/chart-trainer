package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.repository.SettingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SubscribeTickUnitSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    operator fun invoke(): Flow<Result<TickUnit>> =
        settingRepository
            .fetchTickUnitAsFlow()
            .delegateValueResultFlow()
}
