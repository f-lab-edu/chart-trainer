package com.yessorae.presentation.ui.screen.chartgame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.chartgame.component.CandleChartUi
import com.yessorae.presentation.ui.chartgame.component.ChartGameBottomBarUi
import com.yessorae.presentation.ui.chartgame.component.ChartGameTopAppBarUi
import com.yessorae.presentation.ui.chartgame.component.order.TradeOrderUi
import com.yessorae.presentation.ui.chartgame.model.ChartGameEvent
import com.yessorae.presentation.ui.chartgame.model.ChartGameScreenState
import com.yessorae.presentation.ui.chartgame.model.ChartGameScreenUserAction
import com.yessorae.presentation.ui.designsystem.component.ChartTrainerLoadingProgressBar
import com.yessorae.presentation.ui.designsystem.util.showToast
import com.yessorae.presentation.ui.screen.chartgame.component.CandleChartUi
import com.yessorae.presentation.ui.screen.chartgame.component.ChartGameBottomBarUi
import com.yessorae.presentation.ui.screen.chartgame.component.ChartGameTopAppBarUi
import com.yessorae.presentation.ui.screen.chartgame.component.order.TradeOrderUi
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameEvent
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameScreenUserAction
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChartGameRoute(
    viewModel: ChartGameViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToChartGameHistory: (Long) -> Unit
) {
    val state by viewModel.screenState.collectAsState()
    ChartGameScreen(
        state = state
    )

    ChartGameEventHandler(
        screenEvent = viewModel.screenEvent,
        navigateToBack = navigateToBack,
        navigateToChartGameHistory = navigateToChartGameHistory
    )
}

@Composable
fun ChartGameScreen(state: ChartGameScreenState) {
    Scaffold(
        topBar = {
            ChartGameTopAppBarUi(
                isBeforeStart = state.isBeforeStart,
                totalProfit = state.totalProfit,
                totalRateOfProfit = state.rateOfProfit,
                enableChangeChartButton = state.enableChangeChartButton,
                onClickNewChartButton = {
                    state.onUserAction(ChartGameScreenUserAction.ClickNewChartButton)
                },
                onClickChartHistoryButton = {
                    state.onUserAction(ChartGameScreenUserAction.ClickChartGameScreenHistoryButton)
                },
                onClickQuitGameButton = {
                    state.onUserAction(ChartGameScreenUserAction.ClickQuitGameButton)
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CandleChartUi(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    candleStickChart = state.candleStickChart
                )

                ChartGameBottomBarUi(
                    currentTurn = state.currentTurn,
                    totalTurn = state.totalTurn,
                    gameProgress = state.gameProgress,
                    tickUnit = state.tickUnit,
                    enabledBuyButton = state.enabledBuyButton,
                    enabledSellButton = state.enabledSellButton,
                    enabledNextTurnButton = state.enabledNextTurnButton,
                    onClickBuyButton = {
                        state.onUserAction(ChartGameScreenUserAction.ClickBuyButton)
                    },
                    onClickSellButton = {
                        state.onUserAction(ChartGameScreenUserAction.ClickSellButton)
                    },
                    onClickNextTurnButton = {
                        state.onUserAction(ChartGameScreenUserAction.ClickNextTickButton)
                    }
                )
            }

            TradeOrderUi(tradeOrderUi = state.tradeOrderUi)

            ChartTrainerLoadingProgressBar(
                modifier = Modifier.fillMaxSize(),
                show = state.showLoading
            )
        }
    }
}

@Composable
private fun ChartGameEventHandler(
    screenEvent: SharedFlow<ChartGameEvent>,
    navigateToBack: () -> Unit,
    navigateToChartGameHistory: (Long) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        screenEvent.collectLatest { event ->
            when (event) {
                is ChartGameEvent.InputBuyingStockCount -> {
                    context.showToast(R.string.chart_game_toast_trade_buy)
                }

                is ChartGameEvent.InputSellingStockCount -> {
                    context.showToast(R.string.chart_game_toast_trade_sell)
                }

                is ChartGameEvent.TradeFail -> {
                    context.showToast(R.string.chart_game_toast_trade_fail)
                }

                is ChartGameEvent.HardToFetchTrade -> {
                    context.showToast(R.string.chart_game_toast_hard_to_fetch_trade)
                    navigateToBack()
                }

                is ChartGameEvent.GameHasEnded -> {
                    context.showToast(R.string.chart_game_toast_game_has_ended)
                    navigateToChartGameHistory(event.gameId)
                }

                is ChartGameEvent.MoveToBack -> {
                    navigateToBack()
                }

                is ChartGameEvent.MoveToTradeHistory -> {
                    navigateToChartGameHistory(event.gameId)
                }
            }
        }
    }
}

// TODO::NOW #23-navigation 셋업과 함께 Screen level의 Preview 추가
