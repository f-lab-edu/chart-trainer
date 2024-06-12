package com.yessorae.presentation.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.domain.common.DefaultValues.MAX_TOTAL_TURN
import com.yessorae.domain.common.DefaultValues.MIN_TOTAL_TURN
import com.yessorae.domain.common.Result
import com.yessorae.domain.usecase.ChangeCommissionRateSettingUseCase
import com.yessorae.domain.usecase.ChangeTickUnitSettingUseCase
import com.yessorae.domain.usecase.ChangeTotalTurnSettingUseCase
import com.yessorae.domain.usecase.QuitChartGameUseCase
import com.yessorae.domain.usecase.SubscribeHomeDataUseCase
import com.yessorae.presentation.ui.screen.home.model.HomeBottomButtonUi
import com.yessorae.presentation.ui.screen.home.model.HomeScreenEvent
import com.yessorae.presentation.ui.screen.home.model.HomeScreenUserAction
import com.yessorae.presentation.ui.screen.home.model.HomeState
import com.yessorae.presentation.ui.screen.home.model.SettingDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val subscribeUserDataUseCase: SubscribeHomeDataUseCase,
    private val changeCommissionRateSettingUseCase: ChangeCommissionRateSettingUseCase,
    private val changeTickUnitSettingUseCase: ChangeTickUnitSettingUseCase,
    private val changeTotalTurnSettingUseCase: ChangeTotalTurnSettingUseCase,
    private val quitChartGameUseCase: QuitChartGameUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow(HomeState())
    val screenState: StateFlow<HomeState> = _screenState
        .onSubscription {
            subscribeUserData()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeState()
        )

    private val _screenEvent = MutableSharedFlow<HomeScreenEvent>()
    val screenEvent: SharedFlow<HomeScreenEvent> = _screenEvent.asSharedFlow()

    private fun subscribeUserData() {
        subscribeUserDataUseCase().onEach { result ->
            when (result) {
                is Result.Loading -> {
                    _screenState.update { old ->
                        old.copy(
                            screenLoading = true,
                            error = false
                        )
                    }
                }

                is Result.Success -> {
                    with(result.data) {
                        _screenState.update { old ->
                            old.copy(
                                userInfoUi = old.userInfoUi.copy(
                                    currentBalance = user.balance,
                                    winCount = user.winCount,
                                    loseCount = user.loseCount,
                                    averageRateOfProfit = user.averageRateOfProfit.toFloat(),
                                    rateOfWinning = user.rateOfWinning.toFloat(),
                                    rateOfLosing = user.rateOfLosing.toFloat()
                                ),
                                settingInfoUi = old.settingInfoUi.copy(
                                    commissionRate = commissionRate.toFloat(),
                                    tickUnit = tickUnit,
                                    totalTurn = totalTurn
                                ),
                                bottomButtonState = when (lastChartGameIdResult) {
                                    is Result.Loading -> HomeBottomButtonUi.Loading
                                    else -> HomeBottomButtonUi.Success(
                                        hasOnGoingCharGame =
                                        lastChartGameIdResult.getOrNull() != null
                                    )
                                },
                                screenLoading = false,
                                error = false,
                                onUserAction = { userAction ->
                                    handleUserAction(
                                        userAction = userAction,
                                        lastChartGameId = lastChartGameIdResult.getOrNull()
                                    )
                                }
                            )
                        }
                    }
                }

                is Result.Failure -> {
                    _screenState.update { old ->
                        old.copy(
                            screenLoading = false,
                            error = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun handleUserAction(
        userAction: HomeScreenUserAction,
        lastChartGameId: Long?
    ) = viewModelScope.launch {
        when (userAction) {
            is HomeScreenUserAction.ClickStartChartGame -> {
                _screenEvent.emit(
                    HomeScreenEvent.NavigateToChartGameScreen(chartGameId = null)
                )
            }

            is HomeScreenUserAction.ClickKeepGoingChartGame -> {
                lastChartGameId?.let { gameId ->
                    _screenEvent.emit(
                        HomeScreenEvent.NavigateToChartGameScreen(chartGameId = lastChartGameId)
                    )
                }
            }

            is HomeScreenUserAction.ClickQuitInProgressChartGame -> {
                lastChartGameId?.let { gameId ->
                    quitChartGameUseCase(gameId = gameId).launchIn(viewModelScope)
                }
            }

            is HomeScreenUserAction.ClickCommissionRate -> {
                _screenState.update { old ->
                    old.copy(
                        settingDialogState = SettingDialogState.CommissionRate(
                            initialValue = "",
                            onDismissRequest = {
                                dismissSettingDialog()
                            },
                            onDone = { newValue ->
                                val newRate = ("0.$newValue").toFloatOrNull()
                                if (newRate == null) {
                                    viewModelScope.launch {
                                        _screenEvent.emit(
                                            HomeScreenEvent.CommissionRateSettingError
                                        )
                                    }
                                    return@CommissionRate
                                }

                                dismissSettingDialog()
                                changeCommissionRateSettingUseCase(
                                    rate = newRate.toDouble()
                                ).launchIn(viewModelScope)
                            }
                        )
                    )
                }
            }

            is HomeScreenUserAction.ClickTotalTurn -> {
                _screenState.update { old ->
                    old.copy(
                        settingDialogState = SettingDialogState.TotalTurn(
                            initialValue = "",
                            onDismissRequest = {
                                dismissSettingDialog()
                            },
                            onDone = { newValue ->
                                val newTotalTurn = newValue.toIntOrNull()
                                if (
                                    newTotalTurn == null ||
                                    (newTotalTurn in MIN_TOTAL_TURN..MAX_TOTAL_TURN).not()
                                ) {
                                    viewModelScope.launch {
                                        _screenEvent.emit(HomeScreenEvent.TotalTurnSettingError)
                                    }
                                    return@TotalTurn
                                }

                                dismissSettingDialog()
                                changeTotalTurnSettingUseCase(
                                    turn = newTotalTurn
                                ).launchIn(viewModelScope)
                            }
                        )
                    )
                }
            }

            is HomeScreenUserAction.ClickTickUnit -> {
                _screenState.update { old ->
                    old.copy(
                        settingDialogState = SettingDialogState.TickUnit(
                            initialTickUnit = old.settingInfoUi.tickUnit,
                            onDismissRequest = {
                                dismissSettingDialog()
                            },
                            onDone = { newTickUnit ->
                                dismissSettingDialog()
                                changeTickUnitSettingUseCase(
                                    tickUnit = newTickUnit
                                ).launchIn(viewModelScope)
                            }
                        )
                    )
                }
            }
        }
    }

    private fun dismissSettingDialog() {
        _screenState.update { old ->
            old.copy(
                settingDialogState = SettingDialogState.None
            )
        }
    }
}
