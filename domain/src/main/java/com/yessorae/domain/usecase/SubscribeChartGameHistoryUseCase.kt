package com.yessorae.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubscribeChartGameHistoryUseCase @Inject constructor(
    private val chartGameRepository: ChartGameRepository,
    private val chartRepository: ChartRepository
) {
    operator fun invoke(): Flow<PagingData<SuccessData>> {
        return chartGameRepository.fetchPagedChartGameFlow(
            pagingConfig = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false
            )
        ).map { pagingData ->
            pagingData.map { chartGame ->
                val chart = chartRepository.fetchChart(gameId = chartGame.id)
                SuccessData(
                    chartGame = chartGame,
                    chart = chart
                )
            }
        }
    }

    data class SuccessData(
        val chartGame: ChartGame,
        val chart: Chart
    )

    companion object {
        const val PAGE_SIZE = 20
    }
}
