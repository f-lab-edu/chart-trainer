package com.yessorae.presentation.ui.screen.tradehistory.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.designsystem.util.asColor
import com.yessorae.presentation.ui.designsystem.util.asDefaultDisplayText
import com.yessorae.presentation.ui.designsystem.util.asSignedDisplayText
import com.yessorae.presentation.ui.designsystem.util.asText
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryListItem
import kotlin.math.min

@Composable
fun TradeHistoryListItem(
    totalTurn: Int,
    tradeHistory: TradeHistoryListItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {
        TurnChip(
            currentTurn = tradeHistory.turn,
            totalTurn = totalTurn,
            color = tradeHistory.tradeType.asColor(),
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tradeHistory.tradeType.asText(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = tradeHistory.tradeType.asColor(),
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                GuideTextLine(
                    title = {
                        Text(
                            text = tradeHistory.stockPrice.asDefaultDisplayText() +
                                " (${tradeHistory.count}" +
                                "${stringResource(id = R.string.common_stock_unit)})",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    subtitle = {
                        Text(
                            text = tradeHistory.totalPrice.asDefaultDisplayText(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )

                GuideTextLine(
                    title = {
                        Text(
                            text = tradeHistory.commission.asDefaultDisplayText(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    },
                    subtitle = {
                        Text(
                            text = tradeHistory.profit.value.asSignedDisplayText(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (tradeHistory.isProfitPositive) {
                                StockUpColor
                            } else {
                                StockDownColor
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun TurnChip(
    modifier: Modifier = Modifier,
    currentTurn: Int,
    totalTurn: Int,
    color: Color
) {
    var textHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressBar(
            progress = currentTurn.toFloat() / totalTurn,
            modifier = Modifier.size(textHeight),
            progressColor = color,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
            strokeWidth = 10f
        )

        Text(
            text = "$currentTurn/$totalTurn",
            style = MaterialTheme.typography.labelLarge,
            onTextLayout = {
                textHeight = with(density) { it.size.height.toDp() }
            }
        )
    }
}

@Composable
private fun CircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    progressColor: Color = Color.Blue,
    strokeWidth: Float = 10f
) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val radius = (min(canvasWidth, canvasHeight) / 2) - strokeWidth / 2
        val center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)

        drawCircle(
            color = backgroundColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth)
        )
    }
}

@DevicePreviews
@Composable
fun TradeHistoryListItemPreview() {
    TradeHistoryListItem(
        tradeHistory = TradeHistoryListItem(
            id = 1,
            turn = 1,
            tradeType = TradeType.Buy,
            stockPrice = Money(1000.0),
            count = 10,
            totalPrice = Money(10000.0),
            commission = Money(100.0),
            profit = Money(100.0)
        ),
        totalTurn = 10
    )
}
