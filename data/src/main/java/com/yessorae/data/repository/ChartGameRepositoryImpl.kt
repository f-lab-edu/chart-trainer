package com.yessorae.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.local.database.model.TickEntity
import com.yessorae.data.source.local.database.model.TradeEntity
import com.yessorae.data.source.local.database.model.asDomainModel
import com.yessorae.data.source.local.database.model.asEntity
import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChartGameRepositoryImpl @Inject constructor(
    private val localDataSource: ChartTrainerLocalDBDataSource,
    private val chartGamePreferencesDataSource: ChartTrainerPreferencesDataSource,
    @Dispatcher(ChartTrainerDispatcher.IO)
    private val dispatcher: CoroutineDispatcher,
    private val transactionHelper: DatabaseTransactionHelper
) : ChartGameRepository {
    override suspend fun createNewChartGame(chartGame: ChartGame): Long =
        withContext(dispatcher) {
            localDataSource.insertCharGame(entity = chartGame.asEntity())
        }

    override fun fetchChartGameFlow(gameId: Long): Flow<ChartGame> {
        return combine(
            localDataSource.getChartGameAsFlow(id = gameId),
            localDataSource.getTradesAsFlow(gameId = gameId),
            ::Pair
        )
            .distinctUntilChanged()
            .map { (chartGame, newTrades) ->
                val chart = localDataSource.getChart(id = chartGame.chartId)
                val ticks =
                    localDataSource.getTicks(chartId = chart.id).map(TickEntity::asDomainModel)
                chartGame.asDomainModel(
                    chart = chart.asDomainModel(ticks = ticks),
                    trades = newTrades.map(transform = TradeEntity::asDomainModel)
                )
            }
            .flowOn(dispatcher)
    }

    override fun fetchPagedChartGameFlow(pagingConfig: PagingConfig): Flow<PagingData<ChartGame>> {
        return Pager(
            config = pagingConfig
        ) {
            localDataSource.getChartGamePagingSource()
        }
            .flow
            .map { pagingData ->
                pagingData.map { chartGameEntity -> makeChartGame(gameId = chartGameEntity.id) }
            }
    }

    override suspend fun fetchChartGame(gameId: Long): ChartGame = makeChartGame(gameId = gameId)

    private suspend fun makeChartGame(gameId: Long): ChartGame =
        withContext(dispatcher) {
            val chartGameJob = async { localDataSource.getChartGame(id = gameId) }
            val tradesJob = async { localDataSource.getTrades(gameId = gameId) }

            val chartGame = chartGameJob.await()
            val chart = localDataSource.getChart(id = chartGame.chartId)
            val ticks = localDataSource.getTicks(chartId = chart.id).map(TickEntity::asDomainModel)

            chartGame.asDomainModel(
                chart = chart.asDomainModel(ticks = ticks),
                trades = tradesJob.await().map(transform = TradeEntity::asDomainModel)
            )
        }

    override suspend fun updateChartGame(chartGame: ChartGame) =
        withContext(dispatcher) {
            transactionHelper.runTransaction {
                launch {
                    localDataSource.insertOrReplaceAllTrades(
                        entities = chartGame.trades.map(Trade::asEntity)
                    )
                }
                launch {
                    localDataSource.updateChart(entity = chartGame.chart.asEntity())
                }
                launch {
                    localDataSource.updateChartGame(chartGame.asEntity())
                }
            }
        }

    override fun fetchLastChartGameId(): Flow<Long?> =
        chartGamePreferencesDataSource.lastChartGameIdFlow

    override suspend fun clearLastChartGameId() {
        chartGamePreferencesDataSource.clearLastChartGameId()
    }

    override suspend fun updateLastChartGameId(gameId: Long) {
        chartGamePreferencesDataSource.updateLastChartGameId(gameId = gameId)
    }
}
