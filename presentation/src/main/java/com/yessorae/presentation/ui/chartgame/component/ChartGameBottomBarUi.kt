package com.yessorae.presentation.ui.chartgame.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.DefaultTextButton
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.theme.DisabledStockDownColor
import com.yessorae.presentation.ui.designsystem.theme.DisabledStockUpColor
import com.yessorae.presentation.ui.designsystem.theme.DisabledWhiteTextColor
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.theme.WhiteTextColor
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.designsystem.util.Margin
import com.yessorae.presentation.ui.designsystem.util.provideTickUnitText

@Composable
fun ChartGameBottomBarUi(
    currentTurn: Int,
    totalTurn: Int,
    gameProgress: Float,
    tickUnit: TickUnit,
    enabledBuyButton: Boolean,
    enabledSellButton: Boolean,
    enabledNextTurnButton: Boolean,
    onClickBuyButton: () -> Unit,
    onClickSellButton: () -> Unit,
    onClickNextTurnButton: () -> Unit
) {
    val density = LocalDensity.current
    var layoutHeight by remember {
        mutableStateOf(0.dp)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(
                horizontal = Dimen.defaultLayoutSidePadding,
                vertical = 8.dp
            )
            .graphicsLayer {
                layoutHeight = with(density) {
                    size.height.toDp()
                }
            }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                                color = StockUpColor,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("%02d".format(currentTurn))
                        }
                        append("/$totalTurn·${provideTickUnitText(tickUnit = tickUnit)}")
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LinearProgressIndicator(
                    progress = {
                        gameProgress
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                        .clip(MaterialTheme.shapes.medium),
                    color = StockUpColor,
                    strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DefaultTextButton(
                    text = stringResource(id = R.string.common_buy),
                    onClick = onClickBuyButton,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = StockUpColor,
                        contentColor = WhiteTextColor,
                        disabledContainerColor = DisabledStockUpColor,
                        disabledContentColor = DisabledWhiteTextColor
                    ),
                    shape = MaterialTheme.shapes.small,
                    enabled = enabledBuyButton
                )

                Margin(width = 8.dp)

                DefaultTextButton(
                    text = stringResource(id = R.string.common_sell),
                    onClick = onClickSellButton,
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = StockDownColor,
                        contentColor = WhiteTextColor,
                        disabledContainerColor = DisabledStockDownColor,
                        disabledContentColor = DisabledWhiteTextColor
                    ),
                    shape = MaterialTheme.shapes.small,
                    enabled = enabledSellButton
                )
            }
        }

        ChartGameNextButton(
            onClick = onClickNextTurnButton,
            modifier = Modifier
                .padding(start = Dimen.defaultLayoutSidePadding)
                .size(layoutHeight),
            enabled = enabledNextTurnButton
        )
    }
}

@Composable
private fun ChartGameNextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean
) {

    Box(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Icon(
                imageVector = ChartTrainerIcons.NextChart,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )

            Text(
                text = stringResource(id = R.string.chart_game_next),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@DevicePreviews
@Composable
fun ChartGameBottomBarUiPreview() {
    ChartGameBottomBarUi(
        currentTurn = 20,
        totalTurn = 50,
        gameProgress = 20 / 50f,
        tickUnit = TickUnit.DAY,
        enabledBuyButton = true,
        enabledSellButton = false,
        enabledNextTurnButton = true,
        onClickBuyButton = {},
        onClickSellButton = {},
        onClickNextTurnButton = {},
    )
}

