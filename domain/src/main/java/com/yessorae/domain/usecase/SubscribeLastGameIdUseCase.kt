package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.repository.ChartGameRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SubscribeLastGameIdUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository
) {

    operator fun invoke(): Flow<Result<Long?>> =
        chartGameRepository.fetchLastChartGameId().delegateValueResultFlow()
}
