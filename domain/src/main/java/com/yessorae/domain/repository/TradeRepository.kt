package com.yessorae.domain.repository

import com.yessorae.domain.entity.trade.Trade
import kotlinx.coroutines.flow.Flow

interface TradeRepository {
    suspend fun fetchTrades(gameId: Long): List<Trade>
    suspend fun createTrade(trade: Trade): Long
    suspend fun updateTrades(trades: List<Trade>)
}