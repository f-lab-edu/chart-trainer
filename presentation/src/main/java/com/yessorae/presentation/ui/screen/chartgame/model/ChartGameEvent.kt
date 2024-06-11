package com.yessorae.presentation.ui.screen.chartgame.model

sealed interface ChartGameEvent {
    object InputBuyingStockCount : ChartGameEvent
    object InputSellingStockCount : ChartGameEvent
    object HardToFetchTrade : ChartGameEvent
    object TradeFail : ChartGameEvent
    object MoveToBack : ChartGameEvent
    object GameHasEnded : ChartGameEvent
    data class MoveToTradeHistory(val gameId: Long) : ChartGameEvent
}