package com.yessorae.presentation.ui.screen.home.model

sealed interface HomeScreenUserAction {
    object ClickStartChartGame : HomeScreenUserAction
    data class ClickKeepGoingChartGame(
        val lastChartGameId: Long
    ) : HomeScreenUserAction
    data class ClickQuitInProgressChartGame(
        val lastChartGameId: Long
    ) : HomeScreenUserAction
    object ClickCommissionRate : HomeScreenUserAction
    object ClickTotalTurn : HomeScreenUserAction
    object ClickTickUnit : HomeScreenUserAction
    object ClickChartGameHistory : HomeScreenUserAction
}
