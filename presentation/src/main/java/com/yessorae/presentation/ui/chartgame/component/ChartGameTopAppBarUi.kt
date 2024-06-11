package com.yessorae.presentation.ui.chartgame.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.DefaultIconButton
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartGameTopAppBarUi(
    modifier: Modifier = Modifier,
    isStart: Boolean,
    totalProfit: Double,
    totalRateOfProfit: Double,
    onClickNewChartButton: () -> Unit,
    onClickChartHistoryButton: () -> Unit,
    onClickQuitGameButton: () -> Unit
) {
    TopAppBar(
        title = {
            ChartGameTopAppBarTitle(
                isStart = isStart,
                totalProfit = totalProfit,
                totalRateOfProfit = totalRateOfProfit
            )
        },
        actions = {
            DefaultIconButton(
                imageVector = ChartTrainerIcons.ChangeChart,
                onClick = onClickNewChartButton
            )
            DefaultIconButton(
                imageVector = ChartTrainerIcons.TradeList,
                onClick = onClickChartHistoryButton
            )

            DefaultIconButton(
                imageVector = ChartTrainerIcons.Exit,
                onClick = onClickQuitGameButton
            )
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun ChartGameTopAppBarTitle(
    isStart: Boolean,
    totalProfit: Double,
    totalRateOfProfit: Double
) {
    val totalProfitText: String = remember(key1 = totalProfit) {
        "%.3f".format(totalProfit)
    }

    val totalRateOfProfitText: String = remember(key1 = totalRateOfProfit) {
        "%.2f".format(totalRateOfProfit) + "%"
    }

    if (isStart) {
        Text(
            text = stringResource(id = R.string.chart_game_title),
            style = MaterialTheme.typography.titleMedium
        )
    } else {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.common_total_profit),
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = totalProfitText,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (totalProfit > 0) {
                        StockUpColor
                    } else {
                        StockDownColor
                    }
                )
            }

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.common_total_rate_of_profit),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = totalRateOfProfitText,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (totalRateOfProfit > 0) {
                        StockUpColor
                    } else {
                        StockDownColor
                    }
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun ChartGameTopAppBarUiPreview() {
    ChartGameTopAppBarUi(
        isStart = true,
        totalProfit = 0.0,
        totalRateOfProfit = 0.0,
        onClickNewChartButton = {},
        onClickChartHistoryButton = {},
        onClickQuitGameButton = {}
    )
}

@DevicePreviews
@Composable
fun ChartGameTopAppBarUiPreview2() {
    ChartGameTopAppBarUi(
        isStart = false,
        totalProfit = 1234.56421,
        totalRateOfProfit = 120.621,
        onClickNewChartButton = {},
        onClickChartHistoryButton = {},
        onClickQuitGameButton = {}
    )
}