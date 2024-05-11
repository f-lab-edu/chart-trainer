package com.yessorae.domain.repository

import com.yessorae.domain.entity.trade.Trade

interface TradeRepository {
    suspend fun saveTradeHistory(trade: Trade)
}
