package com.yessorae.presentation.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.ChartTrainerLoadingProgressBar
import com.yessorae.presentation.ui.designsystem.component.DefaultIconButton
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.designsystem.util.showToast
import com.yessorae.presentation.ui.screen.home.component.CommissionRateSettingDialog
import com.yessorae.presentation.ui.screen.home.component.HomeBottomButton
import com.yessorae.presentation.ui.screen.home.component.SettingInfoUi
import com.yessorae.presentation.ui.screen.home.component.TickUnitSettingModelBottomSheet
import com.yessorae.presentation.ui.screen.home.component.TotalTurnSettingDialog
import com.yessorae.presentation.ui.screen.home.component.UserInfoUi
import com.yessorae.presentation.ui.screen.home.model.HomeScreenEvent
import com.yessorae.presentation.ui.screen.home.model.HomeScreenUserAction
import com.yessorae.presentation.ui.screen.home.model.HomeState
import com.yessorae.presentation.ui.screen.home.model.SettingDialogState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToChartGame: (Long?) -> Unit,
    navigateToChartGameHistory: () -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    HomeScreen(
        screenState = screenState,
        onUserAction = { userAction ->
            viewModel.onUserAction(userAction = userAction)
        }
    )

    HomeScreenEventHandler(
        screenEvent = viewModel.screenEvent,
        navigateToChartGame = navigateToChartGame,
        navigateToChartGameHistory = navigateToChartGameHistory
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    screenState: HomeState,
    onUserAction: (HomeScreenUserAction) -> Unit
) {
    val dismissDialog = remember {
        {
            onUserAction(HomeScreenUserAction.DismissDialog)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home_title))
                },
                actions = {
                    DefaultIconButton(
                        imageVector = ChartTrainerIcons.ChartGameList,
                        onClick = {
                            onUserAction(HomeScreenUserAction.ClickChartGameHistory)
                        }
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = Dimen.defaultLayoutSidePadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                UserInfoUi(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimen.defaultLayoutSidePadding),
                    userInfoUi = screenState.userInfoUi
                )

                SettingInfoUi(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimen.defaultLayoutSidePadding),
                    settingInfoUi = screenState.settingInfoUi,
                    onClickCommissionRate = {
                        onUserAction(HomeScreenUserAction.ClickCommissionRate)
                    },
                    onClickTotalTurn = {
                        onUserAction(HomeScreenUserAction.ClickTotalTurn)
                    },
                    onClickTickUnit = {
                        onUserAction(HomeScreenUserAction.ClickTickUnit)
                    }
                )

                HomeBottomButton(
                    modifier = Modifier.fillMaxWidth(),
                    homeBottomButtonUi = screenState.bottomButtonState,
                    onClickStartChartGame = {
                        onUserAction(HomeScreenUserAction.ClickStartChartGame)
                    },
                    onClickKeepGoingChartGame = { id ->
                        onUserAction(
                            HomeScreenUserAction.ClickKeepGoingChartGame(
                                lastChartGameId = id
                            )
                        )
                    },
                    onClickQuitInProgressChartGame = { id ->
                        onUserAction(
                            HomeScreenUserAction.ClickQuitInProgressChartGame(
                                lastChartGameId = id
                            )
                        )
                    }
                )
            }

            when (val data = screenState.settingDialogState) {
                is SettingDialogState.CommissionRate -> {
                    CommissionRateSettingDialog(
                        initialValue = data.initialValue,
                        onDismissRequest = dismissDialog,
                        onDone = { newValue ->
                            onUserAction(
                                HomeScreenUserAction.UpdateCommissionRate(
                                    newValue = newValue
                                )
                            )
                        }
                    )
                }

                is SettingDialogState.TotalTurn -> {
                    TotalTurnSettingDialog(
                        initialValue = data.initialValue,
                        onDismissRequest = dismissDialog,
                        onDone = { newValue ->
                            onUserAction(
                                HomeScreenUserAction.UpdateTotalTurn(
                                    newValue = newValue
                                )
                            )
                        }
                    )
                }

                is SettingDialogState.TickUnit -> {
                    TickUnitSettingModelBottomSheet(
                        tickUnit = data.initialTickUnit,
                        onDismissRequest = dismissDialog,
                        onDone = { newValue ->
                            onUserAction(
                                HomeScreenUserAction.UpdateTickUnit(
                                    newValue = newValue
                                )
                            )
                        }
                    )
                }

                else -> {
                    // do nothing
                }
            }

            ChartTrainerLoadingProgressBar(
                modifier = Modifier.fillMaxSize(),
                show = screenState.screenLoading
            )
        }
    }
}

@Composable
fun HomeScreenEventHandler(
    screenEvent: SharedFlow<HomeScreenEvent>,
    navigateToChartGame: (Long?) -> Unit,
    navigateToChartGameHistory: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        screenEvent.collectLatest { event ->
            when (event) {
                is HomeScreenEvent.NavigateToChartGameScreen -> {
                    navigateToChartGame(event.chartGameId)
                }

                is HomeScreenEvent.CommissionRateSettingError -> {
                    context.showToast(id = R.string.home_error_toast_commission_rate_setting)
                }

                is HomeScreenEvent.TotalTurnSettingError -> {
                    context.showToast(id = R.string.home_error_toast_turn_setting)
                }

                is HomeScreenEvent.NavigateToChartGameHistoryScreen -> {
                    navigateToChartGameHistory()
                }
            }
        }
    }
}
