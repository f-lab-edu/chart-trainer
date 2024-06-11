package com.yessorae.presentation.ui.screen.chartgame.model

sealed interface TradeOrderKeyPad {
    data class Number(
        val value: String
    ) : TradeOrderKeyPad

    object DeleteAll : TradeOrderKeyPad

    object Delete : TradeOrderKeyPad
}

enum class PercentageOrderShortCut(val value: Int) {
    PERCENTAGE_10(10),
    PERCENTAGE_25(25),
    PERCENTAGE_50(50),
    PERCENTAGE_100(100)
}
