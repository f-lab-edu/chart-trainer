package com.yessorae.data.repository

import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.local.database.model.TradeEntity
import com.yessorae.data.source.local.database.model.asDomainModel
import com.yessorae.data.source.local.database.model.asEntity
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.repository.TradeRepository
import javax.inject.Inject

class TradeRepositoryImpl @Inject constructor(
    private val localDBDataSource: ChartTrainerLocalDBDataSource
) : TradeRepository {
    override suspend fun fetchTrades(gameId: Long): List<Trade> = localDBDataSource.getTrades(gameId = gameId).map(TradeEntity::asDomainModel)

    override suspend fun createTrade(trade: Trade): Long = localDBDataSource.insertTrade(entity = trade.asEntity())
}
