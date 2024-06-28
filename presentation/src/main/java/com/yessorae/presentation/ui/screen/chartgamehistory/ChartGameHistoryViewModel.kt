package com.yessorae.presentation.ui.screen.chartgamehistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.yessorae.domain.usecase.SubscribeChartGameHistoryUseCase
import com.yessorae.presentation.ui.screen.chartgamehistory.model.ChartGameHistoryScreenEvent
import com.yessorae.presentation.ui.screen.chartgamehistory.model.ChartGameHistoryUserAction
import com.yessorae.presentation.ui.screen.chartgamehistory.model.GameHistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class ChartGameHistoryViewModel @Inject constructor(
    subscribeChartGameHistoryUseCase: SubscribeChartGameHistoryUseCase
) : ViewModel() {
    val pagedChartGameFlow =
        subscribeChartGameHistoryUseCase()
            .map { pagingData ->
                pagingData.map { data ->

                    with(data) {
                        GameHistoryItem(
                            id = chartGame.id,
                            ticker = chart.tickerSymbol,
                            totalTurn = chartGame.totalTurn,
                            tickUnit = chart.tickUnit,
                            totalProfit = chartGame.accumulatedTotalProfit,
                            isTotalProfitPositive = chartGame.accumulatedTotalProfit.value > 0.0,
                            startDate = chart.startDateTime,
                            endDate = chart.endDateTime
                        )
                    }
                }
            }
            .cachedIn(viewModelScope)

    private val _screenEvent = MutableSharedFlow<ChartGameHistoryScreenEvent>()
    val screenEvent: SharedFlow<ChartGameHistoryScreenEvent> = _screenEvent.asSharedFlow()

    fun handleUserAction(userAction: ChartGameHistoryUserAction) =
        viewModelScope.launch {
            when (userAction) {
                is ChartGameHistoryUserAction.ClickChartGameHistory -> {
                    _screenEvent.emit(
                        ChartGameHistoryScreenEvent.NavigateToTradeHistory(
                            chartGameId = userAction.chartGameId
                        )
                    )
                }

                is ChartGameHistoryUserAction.ClickBack -> {
                    _screenEvent.emit(ChartGameHistoryScreenEvent.NavigateToBack)
                }
            }
        }
}
