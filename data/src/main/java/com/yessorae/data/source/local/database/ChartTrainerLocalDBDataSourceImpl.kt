package com.yessorae.data.source.local.database

import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.dao.TradeDao
import com.yessorae.data.source.local.database.model.ChartEntity
import com.yessorae.data.source.local.database.model.ChartGameEntity
import com.yessorae.data.source.local.database.model.TickEntity
import com.yessorae.data.source.local.database.model.TradeEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ChartTrainerLocalDBDataSourceImpl @Inject constructor(
    private val chartGameDao: ChartGameDao,
    private val chartDao: ChartDao,
    private val tradeDao: TradeDao,
    private val tickDao: TickDao
) : ChartTrainerLocalDBDataSource {
    override fun getChartGameAsFlow(id: Long): Flow<ChartGameEntity> =
        chartGameDao.getChartGameAsFlow(id = id)

    override fun getTradesAsFlow(gameId: Long): Flow<List<TradeEntity>> =
        tradeDao.getTradesAsFlow(gameId = gameId)

    override suspend fun getChartGame(id: Long): ChartGameEntity =
        chartGameDao.getChartGame(id = id)

    override suspend fun getTrades(gameId: Long): List<TradeEntity> =
        tradeDao.getTrades(gameId = gameId)

    override suspend fun getChart(id: Long): ChartEntity = chartDao.getChart(id = id)

    override suspend fun getTicks(chartId: Long): List<TickEntity> =
        tickDao.getTicks(chartId = chartId)

    override suspend fun insertCharGame(entity: ChartGameEntity): Long =
        chartGameDao.insert(
            entity = entity
        )

    override suspend fun insertChart(entity: ChartEntity): Long = chartDao.insert(entity = entity)

    override suspend fun insertTicks(entities: List<TickEntity>) =
        tickDao.insertAll(entities = entities)

    override suspend fun updateChartGame(entity: ChartGameEntity) =
        chartGameDao.update(
            entity = entity
        )

    override suspend fun insertOrReplaceAllTrades(entities: List<TradeEntity>) =
        tradeDao.insertOrReplaceAll(
            entities = entities
        )

    override suspend fun updateChart(entity: ChartEntity) = chartDao.update(entity = entity)
}
