package com.yessorae.presentation.ui.screen.home.model

import com.yessorae.domain.entity.tick.TickUnit

sealed interface HomeScreenUserAction {
    object ClickStartChartGame : HomeScreenUserAction

    data class ClickKeepGoingChartGame(
        val lastChartGameId: Long
    ) : HomeScreenUserAction

    data class ClickQuitInProgressChartGame(
        val lastChartGameId: Long
    ) : HomeScreenUserAction

    object ClickCommissionRate : HomeScreenUserAction

    data class UpdateCommissionRate(val newValue: String) : HomeScreenUserAction

    object ClickTotalTurn : HomeScreenUserAction

    data class UpdateTotalTurn(val newValue: String) : HomeScreenUserAction

    object ClickTickUnit : HomeScreenUserAction

    data class UpdateTickUnit(val newValue: TickUnit) : HomeScreenUserAction

    object ClickChartGameHistory : HomeScreenUserAction

    object DismissDialog : HomeScreenUserAction
}
