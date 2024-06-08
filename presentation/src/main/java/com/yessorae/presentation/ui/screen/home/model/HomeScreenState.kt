package com.yessorae.presentation.ui.screen.home.model

import com.yessorae.domain.common.DefaultValues
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.value.Money

data class HomeState(
    val currentBalance: Money = Money(0.0),
    val winCount: Int = 0,
    val loseCount: Int = 0,
    val averageRateOfProfit: Double = 0.0,
    val commissionRate: Double = 0.0,
    val totalTurn: Int = 0,
    val tickUnit: TickUnit = DefaultValues.defaultTickUnit,
    val screenLoading: Boolean = true,
    val chartGameNavigationButtonLoading: Boolean = false,
    val showQuitInProgressChartGameButton: Boolean = false,
    val showKeepGoingChartGameButton: Boolean = false,
    val showStartChartGameButton: Boolean = false,
    val error: Boolean = false,
    val settingBottomSheetState: SettingBottomSheetState = SettingBottomSheetState.None,
    val onUserAction: (HomeScreenUserAction) -> Unit = {}
) {
    val rateOfWinning: Double = if (winCount + loseCount == 0) {
        0.0
    } else {
        winCount / (winCount + loseCount).toDouble()
    }
}

sealed interface SettingBottomSheetState {
    object None : SettingBottomSheetState
    object CommissionRate : SettingBottomSheetState
    object TotalTurn : SettingBottomSheetState
    object TickUnit : SettingBottomSheetState
}
