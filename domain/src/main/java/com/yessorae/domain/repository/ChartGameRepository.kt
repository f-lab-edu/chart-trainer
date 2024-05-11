package com.yessorae.domain.repository

import com.yessorae.domain.entity.ChartGame
import kotlinx.coroutines.flow.Flow

interface ChartGameRepository {
    fun saveChartGame(chartGame: ChartGame): Long
    fun fetchChartFlowStream(gameId: Long): Flow<ChartGame>
    suspend fun fetchChartGame(gameId: Long): ChartGame
    suspend fun updateChartGame(chartGame: ChartGame)
}