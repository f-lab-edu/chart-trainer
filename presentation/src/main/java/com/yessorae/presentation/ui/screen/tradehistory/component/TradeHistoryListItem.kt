package com.yessorae.presentation.ui.screen.tradehistory.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.util.asColor
import com.yessorae.presentation.ui.designsystem.util.asText
import com.yessorae.presentation.ui.screen.tradehistory.model.TradeHistoryListItem
import kotlin.math.absoluteValue

@Composable
fun TradeHistoryListItem(
    modifier: Modifier = Modifier,
    totalTurn: Int,
    tradeHistory: TradeHistoryListItem
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
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
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
                            text = tradeHistory.stockPrice.asDefaultDisplayString() +
                                " (${tradeHistory.count}" +
                                "${stringResource(id = R.string.common_stock_unit)})",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    subtitle = {
                        Text(
                            text = tradeHistory.totalPrice.asDefaultDisplayString(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )

                GuideTextLine(
                    title = {
                        Text(
                            text = tradeHistory.commission.asDefaultDisplayString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    },
                    subtitle = {
                        Text(
                            text = if (tradeHistory.isProfitPositive) {
                                "+"
                            } else {
                                "-"
                            } + "%.2f".format(tradeHistory.commission.value.absoluteValue),
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
