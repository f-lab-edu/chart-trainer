package com.yessorae.presentation.ui.screen.tradehistory.model

import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money

data class TradeHistoryListItem(
    val id: Long,
    val tradeType: TradeType,
    val stockPrice: Money,
    val count: Int,
    val turn: Int,
    val commission: Money,
    // 체결금액
    val totalPrice: Money,
    val profit: Money
) {
    val isProfitPositive: Boolean
        get() = profit.value > 0
}

fun Trade.asTradeHistoryListItemModel(): TradeHistoryListItem {
    return TradeHistoryListItem(
        id = id,
        tradeType = type,
        stockPrice = stockPrice,
        count = count,
        turn = turn,
        commission = commission,
        totalPrice = totalTradeMoney,
        profit = profit
    )
}
