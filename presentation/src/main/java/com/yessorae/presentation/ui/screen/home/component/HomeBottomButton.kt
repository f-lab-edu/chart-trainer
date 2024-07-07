package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.DefaultTextButton
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.theme.TradeTextColor
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.screen.home.model.HomeBottomButtonUi

@Composable
fun HomeBottomButton(
    modifier: Modifier = Modifier,
    homeBottomButtonUi: HomeBottomButtonUi,
    onClickQuitInProgressChartGame: (Long) -> Unit,
    onClickKeepGoingChartGame: (Long) -> Unit,
    onClickStartChartGame: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (homeBottomButtonUi) {
            is HomeBottomButtonUi.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = StockUpColor
                )
            }

            is HomeBottomButtonUi.KeepGoingGameOrQuit -> {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.home_there_is_on_going_game),
                        style = MaterialTheme.typography.labelSmall,
                        color = StockUpColor
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DefaultTextButton(
                            text = stringResource(id = R.string.home_quit_game),
                            onClick = {
                                onClickQuitInProgressChartGame(
                                    homeBottomButtonUi.clickData.lastChartGameId
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                contentColor = StockUpColor
                            ),
                            shape = MaterialTheme.shapes.small
                        )

                        DefaultTextButton(
                            text = stringResource(id = R.string.home_continue_game),
                            onClick = {
                                onClickKeepGoingChartGame(
                                    homeBottomButtonUi.clickData.lastChartGameId
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = StockUpColor,
                                contentColor = TradeTextColor
                            ),
                            shape = MaterialTheme.shapes.small
                        )
                    }
                }
            }

            is HomeBottomButtonUi.NewGame -> {
                DefaultTextButton(
                    text = stringResource(id = R.string.home_start_game),
                    onClick = onClickStartChartGame,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = StockUpColor,
                        contentColor = TradeTextColor
                    ),
                    shape = MaterialTheme.shapes.small
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun HomeBottomOneWayButtonPreview() {
    HomeBottomButton(
        homeBottomButtonUi = HomeBottomButtonUi.NewGame,
        onClickQuitInProgressChartGame = {},
        onClickKeepGoingChartGame = {},
        onClickStartChartGame = {}
    )
}

@DevicePreviews
@Composable
private fun HomeBottomTwoWayButtonPreview() {
    HomeBottomButton(
        homeBottomButtonUi = HomeBottomButtonUi.KeepGoingGameOrQuit(
            clickData = HomeBottomButtonUi.KeepGoingGameOrQuit.ClickData(
                lastChartGameId = 1L
            )
        ),
        onClickQuitInProgressChartGame = {},
        onClickKeepGoingChartGame = {},
        onClickStartChartGame = {}
    )
}

@DevicePreviews
@Composable
private fun HomeBottomLoadingPreview() {
    HomeBottomButton(
        homeBottomButtonUi = HomeBottomButtonUi.Loading,
        onClickStartChartGame = {},
        onClickKeepGoingChartGame = {},
        onClickQuitInProgressChartGame = {}
    )
}
