package com.yessorae.data.source

import androidx.paging.PagingSource
import com.yessorae.data.source.local.database.model.ChartEntity
import com.yessorae.data.source.local.database.model.ChartGameEntity
import com.yessorae.data.source.local.database.model.ChartWithTicksEntity
import com.yessorae.data.source.local.database.model.TickEntity
import com.yessorae.data.source.local.database.model.TradeEntity
import kotlinx.coroutines.flow.Flow

// Repository 가 Dao 에 직접적으로 의존하지 않는 것에 중점을 둠
interface ChartTrainerLocalDBDataSource {
    fun getChartGameAsFlow(id: Long): Flow<ChartGameEntity>
    fun getChartGamePagingSource(): PagingSource<Int, ChartGameEntity>
    suspend fun getChartGame(id: Long): ChartGameEntity
    suspend fun getTrades(gameId: Long): List<TradeEntity>
    suspend fun getChartWithTicks(gameId: Long): ChartWithTicksEntity
    suspend fun insertCharGame(entity: ChartGameEntity): Long
    suspend fun insertChart(entity: ChartEntity): Long
    suspend fun insertTrade(entity: TradeEntity): Long
    suspend fun insertTicks(entities: List<TickEntity>)
    suspend fun updateChartGame(entity: ChartGameEntity)
}
