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
        if (throwUnknownException) throw Exception()

        return chartsFlow.value.find { it.id == id }
            ?: throw IllegalArgumentException("Chart not found")
    }

    override suspend fun insert(entity: ChartEntity): Long {
        val id = super.insert(entity.copy(id = currentId))
        updateFlow()
        return id
    }

    override suspend fun update(entity: ChartEntity) {
        if (throwUnknownException) throw Exception()

        items = items.toMutableList().apply {
            this.find { it.id == entity.id }?.let {
                set(indexOf(it), entity)
            }
        }
        updateFlow()
    }

    override suspend fun delete(entity: ChartEntity) {
        super.delete(entity)
        updateFlow()
    }

    override suspend fun getChartWithTicks(id: Long): ChartWithTicksEntity {
        if (throwUnknownException) throw Exception()

        return ChartWithTicksEntity(
            chart = getChart(id = id),
            ticks = ticksFlow.value.filter { it.chartId == id }
        )
    }

    private fun updateFlow() {
        chartsFlow.value = items
    }
}
