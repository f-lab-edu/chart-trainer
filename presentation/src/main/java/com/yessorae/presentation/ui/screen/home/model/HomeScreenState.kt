package com.yessorae.presentation.ui.screen.home.model

import com.yessorae.domain.common.DefaultValues
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.value.Money

data class HomeState(
    val userInfoUi: UserInfoUi = UserInfoUi(),
    val settingInfoUi: SettingInfoUi = SettingInfoUi(),
    val bottomButtonState: HomeBottomButtonUi = HomeBottomButtonUi.Loading,
    val screenLoading: Boolean = true,
    val error: Boolean = false,
    val settingBottomSheetState: SettingBottomSheetState = SettingBottomSheetState.None,
    val onUserAction: (HomeScreenUserAction) -> Unit = {}
)

data class SettingInfoUi(
    val commissionRate: Float = 0f,
    val totalTurn: Int = 0,
    val tickUnit: TickUnit = DefaultValues.defaultTickUnit
)

data class UserInfoUi(
    val currentBalance: Money = Money(0.0),
    val winCount: Int = 0,
    val loseCount: Int = 0,
    val averageRateOfProfit: Float = 0f
) {
    val rateOfWinning: Float = if (winCount + loseCount == 0) {
        0f
    } else {
        winCount / (winCount + loseCount).toFloat()
    }

    val rateOfLosing: Float = if (rateOfWinning == 0f) {
        0f
    } else {
        1 - rateOfWinning
    }

    val showWinningRateBar: Boolean =
        (rateOfWinning != 0f && rateOfLosing != 0f) && rateOfWinning + rateOfLosing == 1f
}
sealed interface HomeBottomButtonUi {
    object Loading : HomeBottomButtonUi
    data class Success(
        val hasOnGoingCharGame: Boolean
    ) : HomeBottomButtonUi
}

sealed interface SettingBottomSheetState {
    object None : SettingBottomSheetState
    object CommissionRate : SettingBottomSheetState
    object TotalTurn : SettingBottomSheetState
    object TickUnit : SettingBottomSheetState
}
