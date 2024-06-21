package com.yessorae.presentation.ui.screen.tradehistory.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@Composable
fun TradeHistoryListItemGuide(modifier: Modifier = Modifier) {
    Column {
        Row(
            modifier = modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.trade_history_guide_type),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                GuideTextLine(
                    title = {
                        Text(
                            text = stringResource(
                                id = R.string.trade_history_guide_price_and_count
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    subtitle = {
                        Text(
                            text = stringResource(id = R.string.trade_history_guide_total_price),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
                GuideTextLine(
                    title = {
                        Text(
                            text = stringResource(id = R.string.trade_history_guide_commission),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    subtitle = {
                        Text(
                            text = stringResource(id = R.string.trade_history_guide_profit),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }

        HorizontalDivider()
    }
}

@DevicePreviews
@Composable
fun TradeHistoryListItemGuidePreview() {
    TradeHistoryListItemGuide()
}
