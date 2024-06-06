package com.yessorae.presentation.ui.chartgame

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.chartgame.component.CandleChartUi
import com.yessorae.presentation.ui.chartgame.component.ChartGameBottomBarUi
import com.yessorae.presentation.ui.chartgame.component.ChartGameTopAppBarUi
import com.yessorae.presentation.ui.chartgame.component.order.TradeOrderUi
import com.yessorae.presentation.ui.chartgame.model.ChartGameEvent
import com.yessorae.presentation.ui.chartgame.model.ChartGameScreenUserAction
import com.yessorae.presentation.ui.designsystem.component.ChartTrainerLoadingProgressBar
import com.yessorae.presentation.ui.designsystem.util.showToast
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChartGameScreen(viewModel: ChartGameViewModel = viewModel()) {
    val state by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.screenEvent.collectLatest { event ->
            when (event) {
                is ChartGameEvent.InputBuyingStockCount -> {
                    context.showToast(context.getString(R.string.chart_game_toast_trade_buy))
                }

                is ChartGameEvent.InputSellingStockCount -> {
                    context.showToast(context.getString(R.string.chart_game_toast_trade_sell))
                }

                is ChartGameEvent.TradeFail -> {
                    context.showToast(context.getString(R.string.chart_game_toast_trade_fail))
                }

                is ChartGameEvent.HardToFetchTrade -> {
                    context.showToast(
                        context.getString(R.string.chart_game_toast_hard_to_fetch_trade)
                    )
                }

                is ChartGameEvent.MoveToBack -> {
                    // TODO::LATER #23-navigation 셋업과 함께 추가될 내용
                }

                is ChartGameEvent.MoveToTradeHistory -> {
                    // TODO::LATER #6-트레이드 내역 확인 기능과 #23-navigation 셋업과 함께 추가될 내용
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ChartGameTopAppBarUi(
                isBeforeStart = state.isBeforeStart,
                totalProfit = state.totalProfit,
                totalRateOfProfit = state.rateOfProfit,
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

@Preview
@Composable
fun ChartGameScreenPreview() {
    ChartGameScreen()
}
