package com.yessorae.data.source

import com.yessorae.data.source.local.database.model.ChartEntity
import com.yessorae.data.source.local.database.model.ChartGameEntity
import com.yessorae.data.source.local.database.model.TickEntity
import com.yessorae.data.source.local.database.model.TradeEntity
import kotlinx.coroutines.flow.Flow

// Repository 가 Dao 에 직접적으로 의존하지 않는 것에 중점을 둠
interface ChartTrainerLocalDBDataSource {
    fun getChartGameAsFlow(id: Long): Flow<ChartGameEntity>
    fun getTradesAsFlow(gameId: Long): Flow<List<TradeEntity>>
    suspend fun getChartGame(id: Long): ChartGameEntity
    suspend fun getTrades(gameId: Long): List<TradeEntity>
    suspend fun getChart(id: Long): ChartEntity
    suspend fun getTicks(chartId: Long): List<TickEntity>
    suspend fun insertCharGame(entity: ChartGameEntity): Long
    suspend fun updateChartGame(entity: ChartGameEntity)
    suspend fun insertOrReplaceAllTrades(entities: List<TradeEntity>)
    suspend fun updateChart(entity: ChartEntity)
}