package com.yessorae.chartrainer.fake

import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.model.ChartEntity
import com.yessorae.data.source.local.database.model.ChartWithTicksEntity
import com.yessorae.data.source.local.database.model.TickEntity
import kotlinx.coroutines.flow.MutableStateFlow

class FakeChartDao(
    private val ticksFlow: MutableStateFlow<List<TickEntity>>
) : FakeBaseDao<ChartEntity>(), ChartDao {
    private val chartsFlow = MutableStateFlow<List<ChartEntity>>(emptyList())

    override suspend fun getChart(id: Long): ChartEntity {
        return chartsFlow.value.find { it.id == id }
            ?: throw IllegalArgumentException("Chart not found")
    }

    override suspend fun insert(entity: ChartEntity): Long {
        val id = super.insert(entity)
        updateFlow()
        return id
    }

    override suspend fun update(entity: ChartEntity) {
        super.update(entity)
        updateFlow()
    }

    override suspend fun delete(entity: ChartEntity) {
        super.delete(entity)
        updateFlow()
    }

    private fun updateFlow() {
        chartsFlow.value = items
    }

    override suspend fun getChartWithTicks(id: Long): ChartWithTicksEntity {
        return ChartWithTicksEntity(
            chart = getChart(id = id),
            ticks = ticksFlow.value.filter { it.chartId == id }
        )
    }
}
