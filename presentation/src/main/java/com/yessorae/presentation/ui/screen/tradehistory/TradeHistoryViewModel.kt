package com.yessorae.presentation.ui.screen.tradehistory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.domain.common.ChartTrainerLogger
import com.yessorae.domain.common.Result
import com.yessorae.domain.entity.trade.Trade
import com.yessorae.domain.usecase.SubscribeTradeHistoryUseCase
import com.yessorae.presentation.ui.NavigationConstants.CHART_GAME_ID_ARG_KEY
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryScreenEvent
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryScreenModel
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryScreenUserAction
import com.yessorae.presentation.ui.screen.tradehistory.model.asTradeHistoryListItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@HiltViewModel
class TradeHistoryViewModel @Inject constructor(
    private val subscribeTradeHistoryUseCase: SubscribeTradeHistoryUseCase,
    private val logger: ChartTrainerLogger,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val chartGameId: Long =
        savedStateHandle[CHART_GAME_ID_ARG_KEY]
            ?: throw IllegalArgumentException("chartGameId is required")

    private val _tradeHistoryScreen = MutableStateFlow(TradeHistoryScreenModel())
    val tradeHistoryScreen: StateFlow<TradeHistoryScreenModel> =
        _tradeHistoryScreen
            .onSubscription {
                subscribePagedTradeHistory()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = TradeHistoryScreenModel()
            )

    private val _screenEvent = MutableSharedFlow<TradeHistoryScreenEvent>()
    val screenEvent: SharedFlow<TradeHistoryScreenEvent> = _screenEvent.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleFailure(throwable)
    }

    private val scope = viewModelScope + coroutineExceptionHandler

    private fun subscribePagedTradeHistory() =
        scope.launch {
            subscribeTradeHistoryUseCase(gameId = chartGameId).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _tradeHistoryScreen.update { old ->
                            old.copy(loading = true)
                        }
                    }

                    is Result.Success -> {
                        _tradeHistoryScreen.value = TradeHistoryScreenModel(
                            totalTurn = result.data.totalTurn,
                            tradeHistories = result.data.trades.map(
                                transform = Trade::asTradeHistoryListItemModel
                            ),
                            loading = false,
                            none = result.data.trades.isEmpty()
                        )
                    }

                    is Result.Failure -> {
                        handleFailure(result.throwable)
                    }
                }
            }
        }

    fun handleUserAction(userAction: TradeHistoryScreenUserAction) =
        viewModelScope.launch {
            when (userAction) {
                TradeHistoryScreenUserAction.ClickCloseButton -> {
                    _screenEvent.emit(TradeHistoryScreenEvent.NavigateToBack)
                }
            }
        }

    private fun handleFailure(throwable: Throwable) =
        viewModelScope.launch {
            _screenEvent.emit(TradeHistoryScreenEvent.UnknownError)
            logger.cehLog(throwable)
        }
}
