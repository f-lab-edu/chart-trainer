package com.yessorae.domain.repository

import com.yessorae.domain.entity.Chart

interface ChartRepository {
    suspend fun fetchNewChartRandomly(): Chart
}