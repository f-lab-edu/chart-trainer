package com.yessorae.domain.repository

import com.yessorae.domain.entity.trade.Trade

interface TradeRepository {
    suspend fun fetchTrades(gameId: Long): List<Trade>
    suspend fun createTrade(trade: Trade): Long
}
