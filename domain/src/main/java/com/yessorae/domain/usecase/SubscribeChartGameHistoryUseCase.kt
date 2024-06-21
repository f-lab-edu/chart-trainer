package com.yessorae.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.repository.ChartGameRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SubscribeChartGameHistoryUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository
) {
    operator fun invoke(): Flow<PagingData<ChartGame>> {
        return chartGameRepository.fetchPagedChartGameFlow(
            pagingConfig = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false
            )
        )
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
