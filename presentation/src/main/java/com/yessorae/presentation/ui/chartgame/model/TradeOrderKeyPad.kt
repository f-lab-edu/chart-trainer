package com.yessorae.presentation.ui.chartgame.model

sealed interface TradeOrderKeyPad {
    data class Number(
        val value: String
    ) : TradeOrderKeyPad

    object DeleteAll : TradeOrderKeyPad

    object Delete : TradeOrderKeyPad
}
