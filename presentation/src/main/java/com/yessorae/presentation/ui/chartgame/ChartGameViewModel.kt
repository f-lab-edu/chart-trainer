package com.yessorae.presentation.ui.chartgame

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.domain.common.ChartTrainerLogger
import com.yessorae.domain.common.Result
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.usecase.ChangeChartUseCase
import com.yessorae.domain.usecase.QuitChartGameUseCase
import com.yessorae.domain.usecase.SubscribeChartGameUseCase
import com.yessorae.domain.usecase.TradeStockUseCase
import com.yessorae.domain.usecase.UpdateNextTickUseCase
import com.yessorae.presentation.ui.chartgame.model.BuyingOrderUi
import com.yessorae.presentation.ui.chartgame.model.BuyingOrderUiUserAction
import com.yessorae.presentation.ui.chartgame.model.ChartGameEvent
import com.yessorae.presentation.ui.chartgame.model.ChartGameScreenState
import com.yessorae.presentation.ui.chartgame.model.ChartGameScreenUserAction
import com.yessorae.presentation.ui.chartgame.model.SellingOrderUi
import com.yessorae.presentation.ui.chartgame.model.SellingOrderUiUserAction
import com.yessorae.presentation.ui.chartgame.model.TradeOrderKeyPad
import com.yessorae.presentation.ui.chartgame.model.asCandleStickChartUiState
import com.yessorae.presentation.ui.chartgame.model.asTransactionVolume
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val gameIdFromPrevScreen: Long? = savedStateHandle[ARG_KEY_GAME_ID]

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
            subscribeChartGameUseCase(gameId = gameIdFromPrevScreen).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _screenState.update { old -> old.copy(showLoading = true) }
                    is Result.Success -> updateGameData(result.data)
                    is Result.Failure -> handleError(result.throwable)
                }
            }
        }

    private fun updateGameData(data: ChartGame) {
        _screenState.update { old ->
            old.copy(
                currentTurn = data.currentTurn.toString(),
                totalTurn = data.totalTurn.toString(),
                gameProgress = data.currentGameProgress,
                showLoading = false,
                transactionVolume = data.chart.ticks.asTransactionVolume(),
                candleStickChart = data.chart.ticks.asCandleStickChartUiState(),
                onUserAction = { userAction ->
                    handleChartGameScreenUserAction(
                        userAction = userAction,
                        gameId = data.id,
                        ownedAverageStockPrice = data.ownedAverageStockPrice,
                        currentBalance = data.currentBalance,
                        currentStockPrice = data.currentStockPrice,
                        currentTurn = data.currentTurn,
                        ownedStockCount = data.ownedStockCount
                    )
                }
            )
        }
    }

    private fun handleChartGameScreenUserAction(
        userAction: ChartGameScreenUserAction,
        gameId: Long,
        ownedAverageStockPrice: Money,
        currentBalance: Money,
        currentStockPrice: Money,
        currentTurn: Int,
        ownedStockCount: Int
    ) {
        when (userAction) {
            is ChartGameScreenUserAction.ClickNewChartButtonScreen -> {
                changeChart(gameId = gameId)
            }

            is ChartGameScreenUserAction.ClickQuitGameButtonScreen -> {
                quitChartGame(gameId = gameId)
            }

            is ChartGameScreenUserAction.ClickBuyButton -> {
                showBuyOrderUi(
                    gameId = gameId,
                    ownedAverageStockPrice = ownedAverageStockPrice,
                    currentBalance = currentBalance,
                    currentStockPrice = currentStockPrice,
                    currentTurn = currentTurn
                )
            }

            is ChartGameScreenUserAction.ClickSellButton -> {
                showSellOrderUi(
                    gameId = gameId,
                    ownedAverageStockPrice = ownedAverageStockPrice,
                    currentStockPrice = currentStockPrice,
                    currentTurn = currentTurn,
                    ownedStockCount = ownedStockCount
                )
            }

            is ChartGameScreenUserAction.ClickNextTickButton -> {
                updateNextTick(gameId = gameId)
            }

            is ChartGameScreenUserAction.ClickChartGameScreenHistoryButton -> {
                moveToChartTradeHistoryScreen(gameId = gameId)
            }
        }
    }

    private fun changeChart(gameId: Long) {
        changeChartUseCase(gameId = gameId).launchIn(viewModelScope)
    }

    private fun showBuyOrderUi(
        gameId: Long,
        ownedAverageStockPrice: Money,
        currentBalance: Money,
        currentStockPrice: Money,
        currentTurn: Int
    ) {
        val maxAvailableStockCount = (currentBalance / currentStockPrice).value.toInt()
        _screenState.update { old ->
            old.copy(
                buyingOrderUi = BuyingOrderUi(
                    show = true,
                    showKeyPad = false,
                    maxAvailableStockCount = maxAvailableStockCount,
                    currentStockPrice = currentStockPrice.value,
                    onUserAction = { userAction ->
                        handleBuyingOrderUiUserAction(
                            gameId = gameId,
                            ownedAverageStockPrice = ownedAverageStockPrice,
                            currentStockPrice = currentStockPrice,
                            currentTurn = currentTurn,
                            maxAvailableStockCount = maxAvailableStockCount,
                            userAction = userAction
                        )
                    }
                )
            )
        }
    }

    private fun handleBuyingOrderUiUserAction(
        gameId: Long,
        maxAvailableStockCount: Int,
        ownedAverageStockPrice: Money,
        currentStockPrice: Money,
        currentTurn: Int,
        userAction: BuyingOrderUiUserAction
    ) {
        when (userAction) {
            is BuyingOrderUiUserAction.ClickInput -> {
                _screenState.update { old ->
                    old.copy(
                        buyingOrderUi = old.buyingOrderUi?.copy(
                            showKeyPad = true
                        )
                    )
                }
            }

            is BuyingOrderUiUserAction.ClickSellButton -> {
                val count = screenState.value.buyingOrderUi?.stockCountInput

                if (count == null) {
                    emitScreenEvent(event = ChartGameEvent.InputBuyingStockCount)
                    return
                }

                tradeStock(
                    TradeStockUseCase.Param(
                        gameId = gameId,
                        ownedAverageStockPrice = ownedAverageStockPrice,
                        stockPrice = currentStockPrice,
                        count = count.toInt(),
                        turn = currentTurn,
                        type = TradeType.Buy
                    )
                )
            }

            is BuyingOrderUiUserAction.ClickCancelButton -> {
                hideBuyOrderUi()
            }

            is BuyingOrderUiUserAction.DoSystemBack -> {
                hideBuyOrderUi()
            }

            is BuyingOrderUiUserAction.ClickPercentageShortCut -> {
                _screenState.update { old ->
                    old.copy(
                        buyingOrderUi = old.buyingOrderUi?.copy(
                            stockCountInput =
                            (maxAvailableStockCount * (userAction.percent / 100.0)).toString()
                        )
                    )
                }
            }

            is BuyingOrderUiUserAction.ClickKeyPad -> {
                _screenState.update { old ->
                    old.copy(
                        buyingOrderUi = old.buyingOrderUi?.copy(
                            stockCountInput = when (val keyPad = userAction.keyPad) {
                                is TradeOrderKeyPad.Number -> {
                                    val oldValue = old.buyingOrderUi.stockCountInput ?: ""
                                    val newValue = oldValue + keyPad.value
                                    newValue.toInt().coerceAtMost(maxAvailableStockCount).toString()
                                }

                                is TradeOrderKeyPad.Delete -> {
                                    val oldInput = old.buyingOrderUi.stockCountInput
                                    if (oldInput.isNullOrEmpty()) {
                                        null
                                    } else {
                                        oldInput.take(oldInput.length - 1)
                                    }
                                }

                                is TradeOrderKeyPad.DeleteAll -> null
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
                sellingOrderUi = SellingOrderUi(
                    show = true,
                    showKeyPad = false,
                    maxAvailableStockCount = ownedStockCount,
                    currentStockPrice = currentStockPrice.value,
                    onUserAction = { userAction ->
                        handleSellingOrderUiUserAction(
                            gameId = gameId,
                            ownedAverageStockPrice = ownedAverageStockPrice,
                            currentStockPrice = currentStockPrice,
                            currentTurn = currentTurn,
                            ownedStockCount = ownedStockCount,
                            userAction = userAction
                        )
                    }
                )
            )
        }
    }

    private fun handleSellingOrderUiUserAction(
        gameId: Long,
        ownedAverageStockPrice: Money,
        currentStockPrice: Money,
        currentTurn: Int,
        ownedStockCount: Int,
        userAction: SellingOrderUiUserAction
    ) {
        when (userAction) {
            is SellingOrderUiUserAction.ClickInput -> {
                _screenState.update { old ->
                    old.copy(
                        sellingOrderUi = old.sellingOrderUi?.copy(
                            showKeyPad = true
                        )
                    )
                }
            }

            is SellingOrderUiUserAction.ClickSellingButton -> {
                val count = screenState.value.sellingOrderUi?.stockCountInput

                if (count == null) {
                    emitScreenEvent(event = ChartGameEvent.InputSellingStockCount)
                    return
                }
                tradeStock(
                    tradeStockParam = TradeStockUseCase.Param(
                        gameId = gameId,
                        ownedAverageStockPrice = ownedAverageStockPrice,
                        stockPrice = currentStockPrice,
                        count = count.toInt(),
                        turn = currentTurn,
                        type = TradeType.Sell
                    )
                )
            }

            is SellingOrderUiUserAction.ClickCancelButton -> {
                hideSellOrderUi()
            }

            is SellingOrderUiUserAction.DoSystemBack -> {
                hideSellOrderUi()
            }

            is SellingOrderUiUserAction.ClickPercentageShortCut -> {
                _screenState.update { old ->
                    old.copy(
                        sellingOrderUi = old.sellingOrderUi?.copy(
                            stockCountInput =
                            (ownedStockCount * (userAction.percent / 100.0)).toString()
                        )
                    )
                }
            }

            is SellingOrderUiUserAction.ClickKeyPad -> {
                _screenState.update { old ->
                    old.copy(
                        sellingOrderUi = old.sellingOrderUi?.copy(
                            stockCountInput = when (val keyPad = userAction.keyPad) {
                                is TradeOrderKeyPad.Number -> {
                                    val oldValue = old.sellingOrderUi.stockCountInput ?: ""
                                    val newValue = oldValue + keyPad.value
                                    newValue.toInt().coerceAtMost(ownedStockCount).toString()
                                }

                                is TradeOrderKeyPad.Delete -> {
                                    val oldInput = old.sellingOrderUi.stockCountInput
                                    if (oldInput.isNullOrEmpty()) {
                                        null
                                    } else {
                                        oldInput.take(oldInput.length - 1)
                                    }
                                }

                                is TradeOrderKeyPad.DeleteAll -> null
                            }
                        )
                    )
                }
            }
        }
    }

    private fun hideBuyOrderUi() {
        _screenState.update { old ->
            old.copy(
                showLoading = false,
                buyingOrderUi = BuyingOrderUi()
            )
        }
    }

    private fun hideSellOrderUi() {
        _screenState.update { old ->
            old.copy(
                showLoading = false,
                sellingOrderUi = SellingOrderUi()
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
                        when (tradeStockParam.type) {
                            TradeType.Buy -> hideBuyOrderUi()
                            TradeType.Sell -> hideSellOrderUi()
                        }
                    }

                    is Result.Failure -> {
                        emitScreenEvent(ChartGameEvent.TradeFail)
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

    private fun handleError(throwable: Throwable) {
        logger.cehLog(
            throwable = throwable
        )
    }

    companion object {
        // navigation 셋업하면서 위치 다른 파일로 이동할 수 있음
        const val ARG_KEY_GAME_ID = "chart_game_id"
    }
}
