package com.yessorae.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yessorae.domain.entity.ChartGame
import kotlinx.coroutines.flow.Flow

interface ChartGameRepository {
    suspend fun createNewChartGame(chartGame: ChartGame): Long

    fun fetchChartGameFlow(gameId: Long): Flow<ChartGame>

    fun fetchPagedChartGameFlow(pagingConfig: PagingConfig): Flow<PagingData<ChartGame>>

    suspend fun fetchChartGame(gameId: Long): ChartGame

    suspend fun updateChartGame(chartGame: ChartGame)

    fun fetchLastChartGameId(): Flow<Long?>
    suspend fun clearLastChartGameId()
    suspend fun updateLastChartGameId(gameId: Long)
}
