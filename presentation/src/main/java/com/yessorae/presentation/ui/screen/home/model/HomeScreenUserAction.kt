package com.yessorae.presentation.ui.screen.home.model

sealed interface HomeScreenUserAction {
    object ClickStartChartGame : HomeScreenUserAction
    object ClickKeepGoingChartGame : HomeScreenUserAction
    object ClickQuitInProgressChartGame : HomeScreenUserAction
    object ClickCommissionRate : HomeScreenUserAction
    object ClickTotalTurn : HomeScreenUserAction
    object ClickTickUnit : HomeScreenUserAction
}
