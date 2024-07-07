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
import com.yessorae.presentation.ui.designsystem.component.ChartTrainerLoadingProgressBar
import com.yessorae.presentation.ui.designsystem.util.showToast
import com.yessorae.presentation.ui.screen.chartgame.component.CandleChartUi
import com.yessorae.presentation.ui.screen.chartgame.component.ChartGameBottomBarUi
import com.yessorae.presentation.ui.screen.chartgame.component.ChartGameTopAppBarUi
import com.yessorae.presentation.ui.screen.chartgame.component.order.TradeOrderUi
import com.yessorae.presentation.ui.screen.chartgame.model.BuyingOrderUiUserAction
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameEvent
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameScreenState
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameScreenUserAction
import com.yessorae.presentation.ui.screen.chartgame.model.SellingOrderUiUserAction
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
        state = state,
        onScreenUserAction = viewModel::handleChartGameScreenUserAction,
        onBuyingOrderUiUserAction = viewModel::handleBuyingOrderUiUserAction,
        onSellingOrderUiUserAction = viewModel::handleSellOrderUiUserAction
    )

    ChartGameEventHandler(
        screenEvent = viewModel.screenEvent,
        navigateToBack = navigateToBack,
        navigateToChartGameHistory = navigateToChartGameHistory
    )
}

@Composable
fun ChartGameScreen(
    state: ChartGameScreenState,
    onScreenUserAction: (ChartGameScreenUserAction) -> Unit,
    onBuyingOrderUiUserAction: (BuyingOrderUiUserAction) -> Unit,
    onSellingOrderUiUserAction: (SellingOrderUiUserAction) -> Unit
) {
    Scaffold(
        topBar = {
            ChartGameTopAppBarUi(
                isBeforeStart = state.isBeforeStart,
                totalProfit = state.totalProfit,
                totalRateOfProfit = state.rateOfProfit,
                enableChangeChartButton = state.enableChangeChartButton,
                onClickNewChartButton = {
                    onScreenUserAction(
                        ChartGameScreenUserAction.ClickNewChartButton(
                            gameId = state.clickData.gameId
                        )
                    )
                },
                onClickChartHistoryButton = {
                    onScreenUserAction(
                        ChartGameScreenUserAction.ClickChartGameScreenHistoryButton(
                            gameId = state.clickData.gameId
                        )
                    )
                },
                onClickQuitGameButton = {
                    onScreenUserAction(
                        ChartGameScreenUserAction.ClickQuitGameButton(
                            gameId = state.clickData.gameId
                        )
                    )
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
                        onScreenUserAction(
                            with(state.clickData) {
                                ChartGameScreenUserAction.ClickBuyButton(
                                    gameId = gameId,
                                    ownedStockCount = ownedStockCount,
                                    ownedAverageStockPrice = ownedAverageStockPrice,
                                    currentBalance = currentBalance,
                                    currentStockPrice = currentStockPrice,
                                    currentTurn = currentTurn
                                )
                            }
                        )
                    },
                    onClickSellButton = {
                        onScreenUserAction(
                            with(state.clickData) {
                                ChartGameScreenUserAction.ClickSellButton(
                                    gameId = gameId,
                                    ownedAverageStockPrice = ownedAverageStockPrice,
                                    currentStockPrice = currentStockPrice,
                                    currentTurn = currentTurn,
                                    ownedStockCount = ownedStockCount
                                )
                            }
                        )
                    },
                    onClickNextTurnButton = {
                        onScreenUserAction(
                            ChartGameScreenUserAction.ClickNextTickButton(
                                gameId = state.clickData.gameId
                            )
                        )
                    }
                )
            }

            TradeOrderUi(
                tradeOrderUi = state.tradeOrderUi,
                onBuyingUserAction = onBuyingOrderUiUserAction,
                onSellingUserAction = onSellingOrderUiUserAction
            )

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
