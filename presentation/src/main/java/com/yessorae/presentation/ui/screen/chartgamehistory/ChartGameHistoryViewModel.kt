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
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject
import kotlin.math.absoluteValue
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
                pagingData.map { chartGame ->
                    val formatter =
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(
                            Locale.getDefault()
                        )

                    GameHistoryItem(
                        id = chartGame.id,
                        ticker = chartGame.chart.tickerSymbol,
                        totalTurn = chartGame.totalTurn,
                        tickUnit = chartGame.chart.tickUnit,
                        totalProfit = if (chartGame.accumulatedTotalProfit.value > 0.0) {
                            "+"
                        } else {
                            "-"
                        } + "%.2f".format(chartGame.accumulatedTotalProfit.value.absoluteValue),
                        isTotalProfitPositive = chartGame.accumulatedTotalProfit.value > 0.0,
                        time = "${chartGame.chart.startDateTime?.format(formatter) ?: ""} " +
                            "-" +
                            " ${chartGame.chart.endDateTime?.format(formatter) ?: ""}"
                    )
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
