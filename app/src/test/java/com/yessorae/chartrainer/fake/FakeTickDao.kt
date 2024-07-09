package com.yessorae.chartrainer.fake

import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.model.TickEntity
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTickDao : FakeBaseDao<TickEntity>(), TickDao {
    val ticksFlow = MutableStateFlow<List<TickEntity>>(emptyList())

    override suspend fun getTicks(chartId: Long): List<TickEntity> {
        if (throwUnknownException) throw Exception()

        return ticksFlow.value.filter { it.chartId == chartId }
    }

    override suspend fun insert(entity: TickEntity): Long {
        val id = super.insert(entity.copy(id = currentId))
        updateFlow()
        return id
    }

    override suspend fun update(entity: TickEntity) {
        if (throwUnknownException) throw Exception()

        items = items.toMutableList().apply {
            this.find { it.id == entity.id }?.let {
                set(indexOf(it), entity)
            }
        }
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
