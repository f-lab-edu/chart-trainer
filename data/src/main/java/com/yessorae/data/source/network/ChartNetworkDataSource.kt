package com.yessorae.data.source.network

import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit

interface ChartNetworkDataSource {
    suspend fun getChart(
        ticker: String,
        tickUnit: TickUnit,
        from: String,
        to: String
    ): Chart
}
