package com.yessorae.presentation.ui.screen.home.model

import com.yessorae.domain.entity.tick.TickUnit

sealed interface HomeScreenUserAction {
    object ClickStartChartGame : HomeScreenUserAction
    object ClickKeepGoingChartGame : HomeScreenUserAction
    object ClickQuitInProgressChartGame : HomeScreenUserAction
    object ClickCommissionRate : HomeScreenUserAction
    object ClickTotalTurn : HomeScreenUserAction
    object ClickTickUnit : HomeScreenUserAction

    data class ChangeCommissionRate(
        val commissionRate: Double
    ) : HomeScreenUserAction

    data class ChangeTotalTurn(
        val totalTurn: Int
    ) : HomeScreenUserAction

    data class ChangeTickUnit(
        val tickUnit: TickUnit
    ) : HomeScreenUserAction
}
