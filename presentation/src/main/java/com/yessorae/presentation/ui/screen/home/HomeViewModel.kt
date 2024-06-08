package com.yessorae.presentation.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.domain.common.ChartTrainerLogger
import com.yessorae.domain.common.DefaultValues.DEFAULT_COMMISSION_RATE
import com.yessorae.domain.common.DefaultValues.DEFAULT_TOTAL_TURN
import com.yessorae.domain.common.DefaultValues.defaultTickUnit
import com.yessorae.domain.common.Result
import com.yessorae.domain.entity.User
import com.yessorae.domain.usecase.ChangeCommissionRateSettingUseCase
import com.yessorae.domain.usecase.ChangeTickUnitSettingUseCase
import com.yessorae.domain.usecase.ChangeTotalTurnSettingUseCase
import com.yessorae.domain.usecase.QuitChartGameUseCase
import com.yessorae.domain.usecase.SubscribeCommissionRateSettingUseCase
import com.yessorae.domain.usecase.SubscribeLastChartGameIdUseCase
import com.yessorae.domain.usecase.SubscribeTickUnitSettingUseCase
import com.yessorae.domain.usecase.SubscribeTotalTurnSettingUseCase
import com.yessorae.domain.usecase.SubscribeUserUseCase
import com.yessorae.presentation.ui.screen.home.model.HomeScreenEvent
import com.yessorae.presentation.ui.screen.home.model.HomeScreenUserAction
import com.yessorae.presentation.ui.screen.home.model.HomeState
import com.yessorae.presentation.ui.screen.home.model.SettingBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val subscribeUserUseCase: SubscribeUserUseCase,
    private val subscribeCommissionRateSettingUseCase: SubscribeCommissionRateSettingUseCase,
    private val subscribeTickUnitSettingUseCase: SubscribeTickUnitSettingUseCase,
    private val subscribeTotalTurnSettingUseCase: SubscribeTotalTurnSettingUseCase,
    private val lastChartGameIdUseCase: SubscribeLastChartGameIdUseCase,
    private val changeCommissionRateSettingUseCase: ChangeCommissionRateSettingUseCase,
    private val changeTickUnitSettingUseCase: ChangeTickUnitSettingUseCase,
    private val changeTotalTurnSettingUseCase: ChangeTotalTurnSettingUseCase,
    private val quitChartGameUseCase: QuitChartGameUseCase,
    private val logger: ChartTrainerLogger
) : ViewModel() {
    private val _screenState = MutableStateFlow(HomeState())
    val screenState: StateFlow<HomeState> = _screenState
        .onSubscription {
            subscribeData()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeState()
        )

    private val _screenEvent = MutableSharedFlow<HomeScreenEvent>()
    val screenEvent: SharedFlow<HomeScreenEvent> = _screenEvent.asSharedFlow()

    private fun subscribeData() {
        combine(
            subscribeUserUseCase(),
            subscribeCommissionRateSettingUseCase(),
            subscribeTickUnitSettingUseCase(),
            subscribeTotalTurnSettingUseCase(),
            lastChartGameIdUseCase()
        ) { userResult,
            commissionRateResult,
            tickUnitResult,
            totalTurnResult,
            lastChartGameIdResult ->

            val loading = userResult.isLoading ||
                commissionRateResult.isLoading ||
                tickUnitResult.isLoading ||
                totalTurnResult.isLoading

            val error = userResult.isFailure ||
                commissionRateResult.isFailure ||
                tickUnitResult.isFailure ||
                totalTurnResult.isFailure

            val user = userResult.getOrNull() ?: User.createInitialUser()
            val commissionRate = commissionRateResult.getOrNull() ?: DEFAULT_COMMISSION_RATE
            val tickUnit = tickUnitResult.getOrNull() ?: defaultTickUnit
            val totalTurn = totalTurnResult.getOrNull() ?: DEFAULT_TOTAL_TURN
            val lastChartGameId = lastChartGameIdResult.getOrNull()

            _screenState.update { old ->
                old.copy(
                    currentBalance = user.balance,
                    winCount = user.winCount,
                    loseCount = user.loseCount,
                    averageRateOfProfit = user.averageRateOfProfit,
                    commissionRate = commissionRate,
                    tickUnit = tickUnit,
                    totalTurn = totalTurn,
                    screenLoading = loading,
                    chartGameNavigationButtonLoading = lastChartGameIdResult.isLoading,
                    showQuitInProgressChartGameButton = lastChartGameId != null,
                    showKeepGoingChartGameButton = lastChartGameId != null,
                    showStartChartGameButton = lastChartGameId == null,
                    error = loading.not() && error,
                    onUserAction = { userAction ->
                        handleUserAction(
                            userAction = userAction,
                            lastChartGameId = lastChartGameId
                        )
                    }
                )
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
                        settingBottomSheetState = SettingBottomSheetState.CommissionRate
                    )
                }
            }

            is HomeScreenUserAction.ClickTotalTurn -> {
                _screenState.update { old ->
                    old.copy(
                        settingBottomSheetState = SettingBottomSheetState.TotalTurn
                    )
                }
            }

            is HomeScreenUserAction.ClickTickUnit -> {
                _screenState.update { old ->
                    old.copy(
                        settingBottomSheetState = SettingBottomSheetState.TickUnit
                    )
                }
            }

            is HomeScreenUserAction.ChangeCommissionRate -> {
                changeCommissionRateSettingUseCase(
                    rate = userAction.commissionRate
                ).launchIn(viewModelScope)
            }

            is HomeScreenUserAction.ChangeTotalTurn -> {
                changeTotalTurnSettingUseCase(
                    turn = userAction.totalTurn
                ).launchIn(viewModelScope)
            }

            is HomeScreenUserAction.ChangeTickUnit -> {
                changeTickUnitSettingUseCase(
                    tickUnit = userAction.tickUnit
                ).launchIn(viewModelScope)
            }
        }
    }

    private fun handleCommonFailure(failure: Result.Failure) {
        logger.cehLog(failure.throwable)
    }
}
