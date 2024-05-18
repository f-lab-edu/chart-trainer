package com.yessorae.domain.repository

import com.yessorae.domain.entity.ChartGame
import kotlinx.coroutines.flow.Flow

interface ChartGameRepository {
    suspend fun createNewChartGame(chartGame: ChartGame): Long

    fun fetchChartFlow(gameId: Long): Flow<ChartGame>

    suspend fun fetchChartGame(gameId: Long): ChartGame

    suspend fun updateChartGame(chartGame: ChartGame)
}
