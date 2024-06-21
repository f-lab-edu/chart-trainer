package com.yessorae.presentation.ui.screen.chartgamehistory.model

sealed interface ChartGameHistoryScreenEvent {
    data class NavigateToTradeHistory(
        val chartGameId: Long
    ) : ChartGameHistoryScreenEvent

    object NavigateToBack : ChartGameHistoryScreenEvent
}
