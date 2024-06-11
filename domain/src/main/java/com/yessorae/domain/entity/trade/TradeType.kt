package com.yessorae.domain.entity.trade

enum class TradeType {
    Buy,
    Sell;

    fun isBuy(): Boolean = this == Buy
    fun isSell(): Boolean = this == Sell
}
