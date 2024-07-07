package com.yessorae.chartrainer.fake

import com.yessorae.data.source.local.database.dao.TradeDao
import com.yessorae.data.source.local.database.model.TradeEntity
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTradeDao : FakeBaseDao<TradeEntity>(), TradeDao {
    private val tradesFlow = MutableStateFlow<List<TradeEntity>>(emptyList())

    override suspend fun getTrades(gameId: Long): List<TradeEntity> {
        return tradesFlow.value.filter { it.gameId == gameId }
    }

    override suspend fun insert(entity: TradeEntity): Long {
        val id = super.insert(entity)
        updateFlow()
        return id
    }

    override suspend fun update(entity: TradeEntity) {
        super.update(entity)
        updateFlow()
    }

    override suspend fun delete(entity: TradeEntity) {
        super.delete(entity)
        updateFlow()
    }

    private fun updateFlow() {
        tradesFlow.value = items
    }
}
