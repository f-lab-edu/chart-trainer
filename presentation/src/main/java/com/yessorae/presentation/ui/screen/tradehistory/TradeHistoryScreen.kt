package com.yessorae.presentation.ui.screen.tradehistory

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.asMoney
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.designsystem.util.showToast
import com.yessorae.presentation.ui.screen.tradehistory.component.TradeHistoryListItem
import com.yessorae.presentation.ui.screen.tradehistory.component.TradeHistoryListItemGuide
import com.yessorae.presentation.ui.screen.tradehistory.component.TradeHistoryTopAppBar
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryListItem
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryScreenEvent
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryScreenModel
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryScreenUserAction
import kotlinx.coroutines.flow.Flow

@Composable
fun TradeHistoryScreenRoute(
    viewModel: TradeHistoryViewModel = hiltViewModel(),
    navigateToBack: () -> Unit
) {
    val screenModel = viewModel.tradeHistoryScreen.collectAsState()

    TradeHistoryScreenEventHandler(
        screenEvent = viewModel.screenEvent,
        navigateToBack = navigateToBack
    )
    TradeHistoryScreen(
        screenModel = screenModel.value,
        onUserAction = viewModel::handleUserAction
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TradeHistoryScreen(
    screenModel: TradeHistoryScreenModel,
    onUserAction: (TradeHistoryScreenUserAction) -> Unit
) {
    Scaffold(
        topBar = {
            TradeHistoryTopAppBar(
                onClickClose = { onUserAction(TradeHistoryScreenUserAction.ClickCloseButton) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = Dimen.defaultLayoutSidePadding)
            ) {
                // 여기에 item 이 추가될 예정이라서 TradeHistoryListItemGuide 에 대해 stickyHeader 를 사용함

                stickyHeader {
                    TradeHistoryListItemGuide()
                }
                items(
                    items = screenModel.tradeHistories,
                    key = { tradeHistory -> tradeHistory.id }
                ) { tradeHistory ->
                    TradeHistoryListItem(
                        totalTurn = screenModel.totalTurn,
                        tradeHistory = tradeHistory
                    )
                }
            }
        }
    }
}

@Composable
fun TradeHistoryScreenEventHandler(
    screenEvent: Flow<TradeHistoryScreenEvent>,
    navigateToBack: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        screenEvent.collect { event ->
            when (event) {
                is TradeHistoryScreenEvent.NavigateToBack -> {
                    navigateToBack()
                }

                is TradeHistoryScreenEvent.UnknownError -> {
                    context.showToast(R.string.common_error_toast)
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun TradeHistoryScreenPreview() {
    TradeHistoryScreen(
        screenModel = TradeHistoryScreenModel(
            totalTurn = 10,
            tradeHistories = listOf(
                TradeHistoryListItem(
                    id = 1,
                    turn = 1,
                    tradeType = TradeType.BUY,
                    stockPrice = 123.asMoney(),
                    count = 10,
                    totalPrice = 1230.asMoney(),
                    commission = 12.3.asMoney(),
                    profit = (-123).asMoney()
                ),
                TradeHistoryListItem(
                    id = 2,
                    turn = 2,
                    tradeType = TradeType.SELL,
                    stockPrice = 125.asMoney(),
                    count = 10,
                    totalPrice = 1250.asMoney(),
                    commission = 12.4.asMoney(),
                    profit = 7.6.asMoney()
                )
            )
        ),
        onUserAction = {}
    )
}
