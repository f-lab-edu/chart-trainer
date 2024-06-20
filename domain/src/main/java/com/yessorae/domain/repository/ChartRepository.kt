package com.yessorae.domain.repository

import com.yessorae.domain.entity.Chart

interface ChartRepository {
    suspend fun fetchNewChartRandomly(totalTurn: Int): Chart
    suspend fun fetchChart(gameId: Long): Chart
}
