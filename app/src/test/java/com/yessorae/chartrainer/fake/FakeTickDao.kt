package com.yessorae.chartrainer.fake

import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.model.TickEntity
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTickDao : FakeBaseDao<TickEntity>(), TickDao {
    val ticksFlow = MutableStateFlow<List<TickEntity>>(emptyList())

    override suspend fun getTicks(chartId: Long): List<TickEntity> {
        return ticksFlow.value.filter { it.chartId == chartId }
    }

    override suspend fun insert(entity: TickEntity): Long {
        val id = super.insert(entity)
        updateFlow()
        return id
    }

    override suspend fun update(entity: TickEntity) {
        super.update(entity)
        updateFlow()
    }

    override suspend fun delete(entity: TickEntity) {
        super.delete(entity)
        updateFlow()
    }

    private fun updateFlow() {
        ticksFlow.value = items
    }
}
