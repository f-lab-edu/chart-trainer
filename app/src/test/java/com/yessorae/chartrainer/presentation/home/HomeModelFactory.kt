package com.yessorae.chartrainer.presentation.home

import com.yessorae.domain.common.DefaultValues
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.value.Money
import com.yessorae.presentation.ui.screen.home.model.HomeBottomButtonUi
import com.yessorae.presentation.ui.screen.home.model.HomeState
import com.yessorae.presentation.ui.screen.home.model.SettingInfoUi
import com.yessorae.presentation.ui.screen.home.model.UserInfoUi

internal fun createUserInfoUi(
    currentBalance: Money = Money(0.0),
    winCount: Int = 0,
    loseCount: Int = 0,
    averageRateOfProfit: Float = 0f,
    rateOfWinning: Float = 0f,
    rateOfLosing: Float = 0f
) = UserInfoUi(
    currentBalance = currentBalance,
    winCount = winCount,
    loseCount = loseCount,
    averageRateOfProfit = averageRateOfProfit,
    rateOfWinning = rateOfWinning,
    rateOfLosing = rateOfLosing
)

internal fun createSettingInfoUi(
    commissionRate: Float = 0f,
    totalTurn: Int = 0,
    tickUnit: TickUnit = DefaultValues.defaultTickUnit
) = SettingInfoUi(
    commissionRate = commissionRate,
    totalTurn = totalTurn,
    tickUnit = tickUnit
)

internal fun createHomeState(
    userInfoUi: UserInfoUi = createUserInfoUi(),
    settingInfoUi: SettingInfoUi = createSettingInfoUi(),
    bottomButtonState: HomeBottomButtonUi = HomeBottomButtonUi.Loading,
    screenLoading: Boolean = false,
    error: Boolean = false
) = HomeState(
    userInfoUi = userInfoUi,
    settingInfoUi = settingInfoUi,
    bottomButtonState = bottomButtonState,
    screenLoading = screenLoading,
    error = error
)
