package com.yessorae.presentation.ui.screen.chartgamehistory.model

sealed interface ChartGameHistoryUserAction {
    data class ClickChartGameHistory(
        val chartGameId: Long
    ) : ChartGameHistoryUserAction

    object ClickBack : ChartGameHistoryUserAction
}
