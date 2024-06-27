package com.yessorae.presentation.ui.screen.home.model

import com.yessorae.domain.common.DefaultValues
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.entity.value.asMoney

data class HomeState(
    val userInfoUi: UserInfoUi = UserInfoUi(),
    val settingInfoUi: SettingInfoUi = SettingInfoUi(),
    val bottomButtonState: HomeBottomButtonUi = HomeBottomButtonUi.Loading,
    val screenLoading: Boolean = true,
    val error: Boolean = false,
    val settingDialogState: SettingDialogState = SettingDialogState.None
)

data class SettingInfoUi(
    val commissionRate: Float = 0f,
    val totalTurn: Int = 0,
    val tickUnit: TickUnit = DefaultValues.defaultTickUnit
)

data class UserInfoUi(
    val currentBalance: Money = 0.asMoney(),
    val winCount: Int = 0,
    val loseCount: Int = 0,
    val averageRateOfProfit: Float = 0f,
    val rateOfWinning: Float = 0f,
    val rateOfLosing: Float = 0f
) {
    val showWinningRateBar: Boolean =
        (rateOfWinning != 0f && rateOfLosing != 0f) && rateOfWinning + rateOfLosing == 1f
}

sealed interface HomeBottomButtonUi {
    object Loading : HomeBottomButtonUi
    data class Success(
        val hasOnGoingCharGame: Boolean
    ) : HomeBottomButtonUi
}

sealed interface SettingDialogState {
    object None : SettingDialogState
    data class CommissionRate(
        val initialValue: String,
        val onDismissRequest: () -> Unit,
        val onDone: (String) -> Unit
    ) : SettingDialogState

    data class TotalTurn(
        val initialValue: String,
        val onDismissRequest: () -> Unit,
        val onDone: (String) -> Unit
    ) : SettingDialogState

    data class TickUnit(
        val initialTickUnit: com.yessorae.domain.entity.tick.TickUnit,
        val onDone: (com.yessorae.domain.entity.tick.TickUnit) -> Unit,
        val onDismissRequest: () -> Unit
    ) : SettingDialogState
}
