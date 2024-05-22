package com.yessorae.data.repository

import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.dao.TradeDao
import com.yessorae.data.source.local.database.model.TickEntity
import com.yessorae.data.source.local.database.model.TradeEntity
import com.yessorae.data.source.local.database.model.asDomainModel
import com.yessorae.data.source.local.database.model.asEntity
import com.yessorae.data.source.network.polygon.util.DatabaseTransactionHelper
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.repository.ChartGameRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChartGameRepositoryImpl @Inject constructor(
    private val chartGameDao: ChartGameDao,
    private val chartDao: ChartDao,
    private val tradeDao: TradeDao,
    private val tickDao: TickDao,
    @Dispatcher(ChartTrainerDispatcher.IO)
    private val dispatcher: CoroutineDispatcher,
    private val transactionHelper: DatabaseTransactionHelper
) : ChartGameRepository {
    override suspend fun createNewChartGame(chartGame: ChartGame): Long =
        withContext(dispatcher) {
            chartGameDao.insert(entity = chartGame.asEntity())
        }

    override fun fetchChartFlow(gameId: Long): Flow<ChartGame> {
        return combine(
            chartGameDao.getChartGameAsFlow(id = gameId),
            tradeDao.getTradesAsFlow(gameId = gameId),
            ::Pair
        )
            .distinctUntilChanged()
            .map { (chartGame, newTrades) ->
                val chart = chartDao.getChart(id = chartGame.chartId)
                val ticks = tickDao.getTicks(chartId = chart.id).map(TickEntity::asDomainModel)
                chartGame.asDomainModel(
                    chart = chart.asDomainModel(ticks = ticks),
                    trades = newTrades.map(transform = TradeEntity::asDomainModel)
                )
            }
            .flowOn(dispatcher)
    }

    override suspend fun fetchChartGame(gameId: Long): ChartGame =
        withContext(dispatcher) {
            val chartGameJob = async { chartGameDao.getChartGame(id = gameId) }
            val tradesJob = async { tradeDao.getTrades(gameId = gameId) }

            val chartGame = chartGameJob.await()
            val chart = chartDao.getChart(id = chartGame.chartId)
            val ticks = tickDao.getTicks(chartId = chart.id).map(TickEntity::asDomainModel)

            chartGame.asDomainModel(
                chart = chart.asDomainModel(ticks = ticks),
                trades = tradesJob.await().map(transform = TradeEntity::asDomainModel)
            )
        }

    override suspend fun updateChartGame(chartGame: ChartGame) =
        withContext(dispatcher) {
            transactionHelper.runTransaction {
                launch {
                    tradeDao.insertOrReplaceAll(
                        entities = chartGame.trades.map(Trade::asEntity)
                    )
                }
                launch {
                    chartDao.update(entity = chartGame.chart.asEntity())
                }
                launch {
                    chartGameDao.update(chartGame.asEntity())
                }
            }
        }
}
