package com.yessorae.domain.entity.trade

enum class TradeType {
    BUY,
    SELL;

    fun isBuy(): Boolean = this == BUY
    fun isSell(): Boolean = this == SELL
}
