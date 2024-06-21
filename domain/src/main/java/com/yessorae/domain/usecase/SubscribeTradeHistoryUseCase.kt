package com.yessorae.domain.usecase

import com.yessorae.domain.common.Result
import com.yessorae.domain.common.delegateValueResultFlow
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.repository.ChartGameRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SubscribeTradeHistoryUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository
) {
    operator fun invoke(gameId: Long): Flow<Result<ChartGame>> {
        return chartGameRepository.fetchChartGameFlow(gameId = gameId).delegateValueResultFlow()
    }
}
