package com.yessorae.presentation.ui.screen.home.model

sealed interface HomeScreenEvent {
    data class NavigateToChartGameScreen(
        val chartGameId: Long?
    ) : HomeScreenEvent

    object NavigateToChartGameHistoryScreen : HomeScreenEvent

    object CommissionRateSettingError : HomeScreenEvent

    object TotalTurnSettingError : HomeScreenEvent
}
