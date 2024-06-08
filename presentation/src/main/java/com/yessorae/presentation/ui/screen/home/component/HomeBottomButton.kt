package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    onClickStartChartGame: () -> Unit,
    onClickKeepGoingChartGame: () -> Unit,
    onClickQuitInProgressChartGame: () -> Unit
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

            is HomeBottomButtonUi.Success -> {
                if (homeBottomButtonUi.hasOnGoingCharGame) {
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

                        DefaultTextButton(
                            text = stringResource(id = R.string.home_quit_game),
                            onClick = onClickQuitInProgressChartGame,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                contentColor = StockUpColor
                            ),
                            shape = MaterialTheme.shapes.small
                        )

                        DefaultTextButton(
                            text = stringResource(id = R.string.home_continue_game),
                            onClick = onClickKeepGoingChartGame,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = StockUpColor,
                                contentColor = TradeTextColor
                            ),
                            shape = MaterialTheme.shapes.small
                        )
                    }
                } else {
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
}

@DevicePreviews
@Composable
private fun HomeBottomOneWayButtonPreview() {
    HomeBottomButton(
        homeBottomButtonUi = HomeBottomButtonUi.Success(
            hasOnGoingCharGame = false
        ),
        onClickStartChartGame = {},
        onClickKeepGoingChartGame = {},
        onClickQuitInProgressChartGame = {}
    )
}

@DevicePreviews
@Composable
private fun HomeBottomTwoWayButtonPreview() {
    HomeBottomButton(
        homeBottomButtonUi = HomeBottomButtonUi.Success(
            hasOnGoingCharGame = true
        ),
        onClickStartChartGame = {},
        onClickKeepGoingChartGame = {},
        onClickQuitInProgressChartGame = {}
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
