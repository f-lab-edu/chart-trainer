package com.yessorae.chartrainer.fake

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.model.ChartGameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeChartGameDao : FakeBaseDao<ChartGameEntity>(), ChartGameDao {
    private val fakeChartGameTable = MutableStateFlow<List<ChartGameEntity>>(emptyList())

    override fun getChartGameAsFlow(id: Long): Flow<ChartGameEntity> {
        return MutableStateFlow(
            fakeChartGameTable.value.find {
                it.id == id
            } ?: throw IllegalArgumentException("ChartGame not found")
        )
    }

    override suspend fun getChartGame(id: Long): ChartGameEntity {
        return fakeChartGameTable.value.find {
            it.id == id
        } ?: throw IllegalArgumentException("ChartGame not found")
    }

    override fun getChartGamePagingSource(): PagingSource<Int, ChartGameEntity> {
        return object : PagingSource<Int, ChartGameEntity>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChartGameEntity> {
                return LoadResult.Page(
                    data = fakeChartGameTable.value,
                    prevKey = null,
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, ChartGameEntity>): Int? {
                return null
            }
        }
    }

    override fun getChartId(gameId: Long): Long {
        return fakeChartGameTable.value.find {
            it.id == gameId
        }?.chartId ?: throw IllegalArgumentException("ChartGame not found")
    }

    override suspend fun insert(entity: ChartGameEntity): Long {
        val id = super.insert(entity)
        updateFlow()
        return id
    }

    override suspend fun update(entity: ChartGameEntity) {
        super.update(entity)
        updateFlow()
    }

    override suspend fun delete(entity: ChartGameEntity) {
        super.delete(entity)
        updateFlow()
    }

    private fun updateFlow() {
        fakeChartGameTable.value = items
    }
}
