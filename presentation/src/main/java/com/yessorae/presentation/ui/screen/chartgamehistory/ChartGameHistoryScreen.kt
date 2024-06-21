package com.yessorae.presentation.ui.screen.chartgamehistory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.DefaultIconButton
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.screen.chartgamehistory.component.ChartGameHistoryListItem
import com.yessorae.presentation.ui.screen.chartgamehistory.model.ChartGameHistoryScreenEvent
import com.yessorae.presentation.ui.screen.chartgamehistory.model.ChartGameHistoryUserAction
import com.yessorae.presentation.ui.screen.chartgamehistory.model.GameHistoryItem
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ChartGameHistoryRoute(
    viewModel: ChartGameHistoryViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToTradeHistory: (chartGameId: Long) -> Unit
) {
    val pagedChartGame = viewModel.pagedChartGameFlow.collectAsLazyPagingItems()

    ChartGameHistoryScreenEventHandler(
        screenEvent = viewModel.screenEvent,
        navigateToBack = navigateToBack,
        navigateToTradeHistory = navigateToTradeHistory
    )

    ChartGameHistoryScreen(
        pagedChartGame = pagedChartGame,
        handleUserAction = viewModel::handleUserAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChartGameHistoryScreen(
    pagedChartGame: LazyPagingItems<GameHistoryItem>,
    handleUserAction: (ChartGameHistoryUserAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.chart_game_history_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    DefaultIconButton(
                        imageVector = ChartTrainerIcons.Back,
                        onClick = {
                            handleUserAction(ChartGameHistoryUserAction.ClickBack)
                        }
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (pagedChartGame.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator()
                }

                is LoadState.Error -> {
                    Text(
                        text = stringResource(id = R.string.common_error_toast),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.displayMedium
                    )
                }

                is LoadState.NotLoading -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(pagedChartGame.itemCount) { index ->
                            pagedChartGame[index]?.let { item ->
                                ChartGameHistoryListItem(
                                    item = item,
                                    onClick = {
                                        handleUserAction(
                                            ChartGameHistoryUserAction.ClickChartGameHistory(
                                                chartGameId = item.id
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChartGameHistoryScreenEventHandler(
    screenEvent: SharedFlow<ChartGameHistoryScreenEvent>,
    navigateToBack: () -> Unit,
    navigateToTradeHistory: (chartGameId: Long) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        screenEvent.collect { event ->
            when (event) {
                is ChartGameHistoryScreenEvent.NavigateToTradeHistory -> {
                    navigateToTradeHistory(event.chartGameId)
                }

                is ChartGameHistoryScreenEvent.NavigateToBack -> {
                    navigateToBack()
                }
            }
        }
    }
}
