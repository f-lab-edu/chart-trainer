package com.yessorae.presentation.ui.chartgame.model

sealed interface ChartGameEvent {
    object InputBuyingStockCount : ChartGameEvent
    object InputSellingStockCount : ChartGameEvent
    object TradeFail : ChartGameEvent
    object MoveToBack : ChartGameEvent
    data class MoveToTradeHistory(val gameId: Long) : ChartGameEvent
}
