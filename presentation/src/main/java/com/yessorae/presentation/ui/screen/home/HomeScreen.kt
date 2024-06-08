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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.ChartTrainerLoadingProgressBar
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.screen.home.component.HomeBottomButton
import com.yessorae.presentation.ui.screen.home.component.SettingInfoUi
import com.yessorae.presentation.ui.screen.home.component.UserInfoUi
import com.yessorae.presentation.ui.screen.home.model.HomeScreenEvent
import com.yessorae.presentation.ui.screen.home.model.HomeScreenUserAction
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    HomeScreenEventHandler(screenEvent = viewModel.screenEvent)

    val screenState by viewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home_title))
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
                        screenState.onUserAction(HomeScreenUserAction.ClickCommissionRate)
                    },
                    onClickTotalTurn = {
                        screenState.onUserAction(HomeScreenUserAction.ClickTotalTurn)
                    },
                    onClickTickUnit = {
                        screenState.onUserAction(HomeScreenUserAction.ClickTickUnit)
                    }
                )

                HomeBottomButton(
                    modifier = Modifier.fillMaxWidth(),
                    homeBottomButtonUi = screenState.bottomButtonState,
                    onClickStartChartGame = {
                        screenState.onUserAction(HomeScreenUserAction.ClickStartChartGame)
                    },
                    onClickKeepGoingChartGame = {
                        screenState.onUserAction(HomeScreenUserAction.ClickKeepGoingChartGame)
                    },
                    onClickQuitInProgressChartGame = {
                        screenState.onUserAction(HomeScreenUserAction.ClickQuitInProgressChartGame)
                    }
                )
            }

            ChartTrainerLoadingProgressBar(
                modifier = Modifier.fillMaxSize(),
                show = screenState.screenLoading
            )
        }
    }
}

@Composable
fun HomeScreenEventHandler(screenEvent: SharedFlow<HomeScreenEvent>) {
    LaunchedEffect(key1 = Unit) {
        screenEvent.collectLatest { event ->
            when (event) {
                is HomeScreenEvent.NavigateToChartGameScreen -> {
                    // TODO::LATER CT-23 navigation 셋업 이후에 구현
                }
            }
        }
    }
}
