package com.yessorae.presentation.ui.screen.chartgame

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.domain.common.ChartTrainerLogger
import com.yessorae.domain.common.Result
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.usecase.ChangeChartUseCase
import com.yessorae.domain.usecase.QuitChartGameUseCase
import com.yessorae.domain.usecase.SubscribeChartGameUseCase
import com.yessorae.domain.usecase.TradeStockUseCase
import com.yessorae.domain.usecase.UpdateNextTickUseCase
import com.yessorae.presentation.ui.NavigationConstants.CHART_GAME_ID_ARG_KEY
import com.yessorae.presentation.ui.screen.chartgame.model.BuyingOrderUiUserAction
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameEvent
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameScreenState
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameScreenUserAction
import com.yessorae.presentation.ui.screen.chartgame.model.SellingOrderUiUserAction
import com.yessorae.presentation.ui.screen.chartgame.model.TradeOrderKeyPad
import com.yessorae.presentation.ui.screen.chartgame.model.TradeOrderUi
import com.yessorae.presentation.ui.screen.chartgame.model.TradeOrderUi.Companion.copyWith
import com.yessorae.presentation.ui.screen.chartgame.model.asCandleStickChartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChartGameViewModel @Inject constructor(
    private val subscribeChartGameUseCase: SubscribeChartGameUseCase,
    private val changeChartUseCase: ChangeChartUseCase,
    private val tradeStockUseCase: TradeStockUseCase,
    private val updateNextTickUseCase: UpdateNextTickUseCase,
    private val quitChartGameUseCase: QuitChartGameUseCase,
    private val logger: ChartTrainerLogger,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _screenState = MutableStateFlow(ChartGameScreenState())
    val screenState: StateFlow<ChartGameScreenState> =
        _screenState
            .onSubscription {
                subscribeChartGame()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ChartGameScreenState()
            )

    private val _screenEvent = MutableSharedFlow<ChartGameEvent>()
    val screenEvent: SharedFlow<ChartGameEvent> = _screenEvent.asSharedFlow()

    private fun subscribeChartGame() =
        viewModelScope.launch {
            subscribeChartGameUseCase(
                gameId = savedStateHandle.get<String>(CHART_GAME_ID_ARG_KEY)?.toLongOrNull()
            ).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _screenState.update { old -> old.copy(showLoading = true) }
                    }

                    is Result.Success -> {
                        updateGameData(
                            chartGame = result.data.chartGame,
                            visibleTicks = result.data.visibleTicks
                        )
                    }

                    is Result.Failure -> {
                        when (result.throwable) {
                            is ChartGameException.HardToFetchTradeException -> {
                                emitScreenEvent(event = ChartGameEvent.HardToFetchTrade)
                            }

                            else -> {
                                handleNotHandlingFailure(result.throwable)
                            }
                        }
                    }
                }
            }
        }

    private fun updateGameData(
        chartGame: ChartGame,
        visibleTicks: List<Tick>
    ) = with(chartGame) {
        _screenState.update { old ->
            old.copy(
                currentTurn = currentTurn,
                totalTurn = totalTurn,
                totalProfit = accumulatedTotalProfit.value,
                rateOfProfit = accumulatedRateOfProfit,
                gameProgress = currentGameProgress,
                showLoading = false,
                candleStickChart = visibleTicks.asCandleStickChartUiState(),
                isGameComplete = isGameComplete,
                isGameEnd = isGameEnd,
                clickData = ChartGameScreenState.ClickData(
                    gameId = id,
                    ownedAverageStockPrice = averageStockPrice,
                    currentBalance = currentBalance,
                    currentStockPrice = closeStockPrice,
                    currentTurn = currentTurn,
                    ownedStockCount = totalStockCount
                )
            )
        }

        if (isGameComplete) {
            emitScreenEvent(event = ChartGameEvent.GameHasEnded(gameId = id))
        }
    }

    fun handleChartGameScreenUserAction(userAction: ChartGameScreenUserAction) {
        when (userAction) {
            is ChartGameScreenUserAction.ClickNewChartButton -> {
                changeChart(gameId = userAction.gameId)
            }

            is ChartGameScreenUserAction.ClickQuitGameButton -> {
                quitChartGame(gameId = userAction.gameId)
            }

            is ChartGameScreenUserAction.ClickBuyButton -> {
                showBuyOrderUi(
                    gameId = userAction.gameId,
                    ownedStockCount = userAction.ownedStockCount,
                    ownedAverageStockPrice = userAction.ownedAverageStockPrice,
                    currentBalance = userAction.currentBalance,
                    currentStockPrice = userAction.currentStockPrice,
                    currentTurn = userAction.currentTurn
                )
            }

            is ChartGameScreenUserAction.ClickSellButton -> {
                showSellOrderUi(
                    gameId = userAction.gameId,
                    ownedAverageStockPrice = userAction.ownedAverageStockPrice,
                    currentStockPrice = userAction.currentStockPrice,
                    currentTurn = userAction.currentTurn,
                    ownedStockCount = userAction.ownedStockCount
                )
            }

            is ChartGameScreenUserAction.ClickNextTickButton -> {
                updateNextTick(gameId = userAction.gameId)
            }

            is ChartGameScreenUserAction.ClickChartGameScreenHistoryButton -> {
                moveToChartTradeHistoryScreen(gameId = userAction.gameId)
            }
        }
    }

    private fun changeChart(gameId: Long) =
        viewModelScope.launch {
            changeChartUseCase(gameId = gameId).collect { result ->
                when (result) {
                    is Result.Loading -> _screenState.update { old -> old.copy(showLoading = true) }
                    else -> _screenState.update { old -> old.copy(showLoading = false) }
                }
            }
        }

    private fun showBuyOrderUi(
        gameId: Long,
        ownedStockCount: Int,
        ownedAverageStockPrice: Money,
        currentBalance: Money,
        currentStockPrice: Money,
        currentTurn: Int
    ) {
        val maxAvailableStockCount = (currentBalance / currentStockPrice).value.toInt()
        _screenState.update { old ->
            old.copy(
                tradeOrderUi = TradeOrderUi.Buy(
                    showKeyPad = false,
                    maxAvailableStockCount = maxAvailableStockCount,
                    currentStockPrice = currentStockPrice.value,
                    clickData = TradeOrderUi.Buy.ClickData(
                        gameId = gameId,
                        ownedStockCount = ownedStockCount,
                        ownedAverageStockPrice = ownedAverageStockPrice,
                        currentStockPrice = currentStockPrice,
                        currentTurn = currentTurn,
                        maxAvailableStockCount = maxAvailableStockCount
                    )
                )
            )
        }
    }

    fun handleBuyingOrderUiUserAction(userAction: BuyingOrderUiUserAction) {
        when (userAction) {
            is BuyingOrderUiUserAction.ClickShowKeyPad -> {
                _screenState.update { old ->
                    old.copy(
                        tradeOrderUi = old.tradeOrderUi.copyWith(
                            showKeyPad = true
                        )
                    )
                }
            }

            is BuyingOrderUiUserAction.ClickTrade -> {
                val count = userAction.stockCountInput?.toIntOrNull()

                if (count == null) {
                    emitScreenEvent(event = ChartGameEvent.InputBuyingStockCount)
                    return
                }

                tradeStock(
                    TradeStockUseCase.Param(
                        gameId = userAction.gameId,
                        ownedStockCount = userAction.ownedStockCount,
                        ownedAverageStockPrice = userAction.ownedAverageStockPrice,
                        stockPrice = userAction.currentStockPrice,
                        count = count,
                        turn = userAction.currentTurn,
                        type = TradeType.BUY
                    )
                )
            }

            is BuyingOrderUiUserAction.ClickCancelButton -> {
                hideTradeOrderUi()
            }

            is BuyingOrderUiUserAction.DoSystemBack -> {
                hideTradeOrderUi()
            }

            is BuyingOrderUiUserAction.ClickRatioShortCut -> {
                _screenState.update { old ->
                    val percentage = (userAction.percentage.value / 100.0)
                    val newStockCount = userAction.maxAvailableStockCount * percentage
                    old.copy(
                        tradeOrderUi = old.tradeOrderUi.copyWith(
                            stockCountInput = newStockCount.toInt().toString()
                        )
                    )
                }
            }

            is BuyingOrderUiUserAction.ClickKeyPad -> {
                _screenState.update { old ->
                    old.copy(
                        tradeOrderUi = old.tradeOrderUi.copyWith(
                            stockCountInput = when (val keyPad = userAction.keyPad) {
                                is TradeOrderKeyPad.Number -> {
                                    val oldValue = userAction.stockCountInput
                                    if (oldValue.isEmpty() && keyPad.value == "0") {
                                        ""
                                    } else {
                                        val newValue = oldValue + keyPad.value

                                        newValue.toIntOrNull()
                                            ?.coerceAtMost(
                                                maximumValue = userAction.maxAvailableStockCount
                                            )
                                            ?.toString()
                                            ?: run {
                                                emitScreenEvent(event = ChartGameEvent.InputBuyingStockCount)
                                                oldValue
                                            }
                                    }
                                }

                                is TradeOrderKeyPad.Delete -> {
                                    val oldInput = userAction.stockCountInput
                                    if (oldInput.isNotEmpty()) {
                                        oldInput.take(oldInput.length - 1)
                                    } else {
                                        oldInput
                                    }
                                }

                                is TradeOrderKeyPad.DeleteAll -> ""
                            }
                        )
                    )
                }
            }
        }
    }

    private fun showSellOrderUi(
        gameId: Long,
        ownedAverageStockPrice: Money,
        currentStockPrice: Money,
        currentTurn: Int,
        ownedStockCount: Int
    ) {
        _screenState.update { old ->
            old.copy(
                tradeOrderUi = TradeOrderUi.Sell(
                    showKeyPad = false,
                    maxAvailableStockCount = ownedStockCount,
                    currentStockPrice = currentStockPrice.value,
                    clickData = TradeOrderUi.Sell.ClickData(
                        gameId = gameId,
                        ownedStockCount = ownedStockCount,
                        ownedAverageStockPrice = ownedAverageStockPrice,
                        currentStockPrice = currentStockPrice,
                        currentTurn = currentTurn
                    )
                )
            )
        }
    }

    fun handleSellOrderUiUserAction(userAction: SellingOrderUiUserAction) {
        when (userAction) {
            is SellingOrderUiUserAction.ClickShowKeyPad -> {
                _screenState.update { old ->
                    old.copy(
                        tradeOrderUi = old.tradeOrderUi.copyWith(
                            showKeyPad = true
                        )
                    )
                }
            }

            is SellingOrderUiUserAction.ClickTrade -> {
                val count = userAction.stockCountInput.toIntOrNull()

                if (count == null) {
                    emitScreenEvent(event = ChartGameEvent.InputSellingStockCount)
                    return
                }

                tradeStock(
                    tradeStockParam = TradeStockUseCase.Param(
                        gameId = userAction.gameId,
                        ownedStockCount = userAction.ownedStockCount,
                        ownedAverageStockPrice = userAction.ownedAverageStockPrice,
                        stockPrice = userAction.currentStockPrice,
                        count = count,
                        turn = userAction.currentTurn,
                        type = TradeType.SELL
                    )
                )
            }

            is SellingOrderUiUserAction.ClickCancelButton -> {
                hideTradeOrderUi()
            }

            is SellingOrderUiUserAction.DoSystemBack -> {
                hideTradeOrderUi()
            }

            is SellingOrderUiUserAction.ClickRatioShortCut -> {
                _screenState.update { old ->
                    val percentage = (userAction.percentage.value / 100.0)
                    val newStockCount = userAction.ownedStockCount * percentage
                    old.copy(
                        tradeOrderUi = old.tradeOrderUi.copyWith(
                            stockCountInput = newStockCount.toInt().toString()
                        )
                    )
                }
            }

            is SellingOrderUiUserAction.ClickKeyPad -> {
                _screenState.update { old ->
                    old.copy(
                        tradeOrderUi = old.tradeOrderUi.copyWith(
                            stockCountInput = when (val keyPad = userAction.keyPad) {
                                is TradeOrderKeyPad.Number -> {
                                    val oldValue = userAction.stockCountInput
                                    val newValue = oldValue + keyPad.value
                                    newValue.toIntOrNull()
                                        ?.coerceAtMost(userAction.ownedStockCount)
                                        ?.toString()
                                        ?: run {
                                            emitScreenEvent(ChartGameEvent.InputSellingStockCount)
                                            oldValue
                                        }
                                }

                                is TradeOrderKeyPad.Delete -> {
                                    val oldInput = userAction.stockCountInput
                                    if (oldInput.isNotEmpty()) {
                                        oldInput.take(oldInput.length - 1)
                                    } else {
                                        oldInput
                                    }
                                }

                                is TradeOrderKeyPad.DeleteAll -> ""
                            }
                        )
                    )
                }
            }
        }
    }

    private fun hideTradeOrderUi() {
        _screenState.update { old ->
            old.copy(
                showLoading = false,
                tradeOrderUi = TradeOrderUi.Hide
            )
        }
    }

    private fun tradeStock(tradeStockParam: TradeStockUseCase.Param) =
        viewModelScope.launch {
            tradeStockUseCase(param = tradeStockParam).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _screenState.update { old -> old.copy(showLoading = true) }
                    }

                    is Result.Success -> {
                        hideTradeOrderUi()
                        _screenState.update { old -> old.copy(showLoading = false) }
                    }

                    is Result.Failure -> {
                        emitScreenEvent(ChartGameEvent.TradeFail)
                        hideTradeOrderUi()
                    }
                }
            }
        }

    private fun updateNextTick(gameId: Long) {
        updateNextTickUseCase(gameId = gameId).launchIn(viewModelScope)
    }

    private fun quitChartGame(gameId: Long) {
        // TODO::LATER 바로 뒤로가지 않고 컨펌 다이얼로그 보여주기
        quitChartGameUseCase(gameId = gameId).launchIn(viewModelScope)
        emitScreenEvent(event = ChartGameEvent.MoveToBack)
    }

    private fun moveToChartTradeHistoryScreen(gameId: Long) =
        viewModelScope.launch {
            emitScreenEvent(event = ChartGameEvent.MoveToTradeHistory(gameId = gameId))
        }

    private fun emitScreenEvent(event: ChartGameEvent) =
        viewModelScope.launch {
            _screenEvent.emit(event)
        }

    private fun handleNotHandlingFailure(throwable: Throwable) {
        _screenState.update { old -> old.copy(showLoading = false) }
        emitScreenEvent(event = ChartGameEvent.UnknownError)
        logger.cehLog(
            throwable = throwable
        )
    }
}
