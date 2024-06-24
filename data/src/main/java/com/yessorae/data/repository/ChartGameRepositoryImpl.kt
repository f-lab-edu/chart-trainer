package com.yessorae.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.ChartTrainerPreferencesDataSource
import com.yessorae.data.source.local.database.model.asDomainModel
import com.yessorae.data.source.local.database.model.asEntity
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.repository.ChartGameRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChartGameRepositoryImpl @Inject constructor(
    private val localDataSource: ChartTrainerLocalDBDataSource,
    private val chartGamePreferencesDataSource: ChartTrainerPreferencesDataSource,
    @Dispatcher(ChartTrainerDispatcher.IO)
    private val dispatcher: CoroutineDispatcher
) : ChartGameRepository {
    override suspend fun createNewChartGame(chartGame: ChartGame): Long =
        withContext(dispatcher) {
            localDataSource.insertCharGame(entity = chartGame.asEntity())
        }

    override fun fetchChartGameFlow(gameId: Long): Flow<ChartGame> {
        return localDataSource.getChartGameAsFlow(id = gameId)
            .flowOn(dispatcher)
            .map { chartGameEntity ->
                chartGameEntity.asDomainModel()
            }
    }

    override fun fetchPagedChartGameFlow(pagingConfig: PagingConfig): Flow<PagingData<ChartGame>> {
        return Pager(
            config = pagingConfig
        ) {
            localDataSource.getChartGamePagingSource()
        }
            .flow
            .map { pagingData ->
                pagingData.map { chartGameEntity -> chartGameEntity.asDomainModel() }
            }
    }

    override suspend fun fetchChartGame(gameId: Long): ChartGame =
        localDataSource.getChartGame(id = gameId).asDomainModel()

    override suspend fun updateChartGame(chartGame: ChartGame): Unit =
        withContext(dispatcher) {
            launch {
                localDataSource.updateChartGame(chartGame.asEntity())
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
