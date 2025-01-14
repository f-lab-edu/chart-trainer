package com.yessorae.presentation.ui.screen.chartgamehistory.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.value.asMoney
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.designsystem.util.asDisplayTotalProfit
import com.yessorae.presentation.ui.designsystem.util.getDisplayDateRangeText
import com.yessorae.presentation.ui.screen.chartgamehistory.model.GameHistoryItem
import java.time.LocalDateTime

class ChartGameHistoryListItem

@Composable
fun ChartGameHistoryListItem(
    item: GameHistoryItem,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                vertical = 8.dp,
                horizontal = Dimen.defaultLayoutSidePadding
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.ticker,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = getDisplayDateRangeText(item.startDate, item.endDate),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.totalProfit.asDisplayTotalProfit(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = if (item.isTotalProfitPositive) {
                    StockUpColor
                } else {
                    StockDownColor
                }
            )

            Icon(
                imageVector = ChartTrainerIcons.NavigateArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceContainerHigh
            )
        }
    }
}

@DevicePreviews
@Composable
fun PreviewChartGameHistoryListItem() {
    val item = GameHistoryItem(
        id = 1L,
        ticker = "AAPL",
        totalTurn = 10,
        tickUnit = TickUnit.DAY,
        totalProfit = 10.asMoney(),
        isTotalProfitPositive = true,
        startDate = LocalDateTime.of(2024, 1, 1, 0, 0),
        endDate = LocalDateTime.of(2026, 1, 1, 0, 0)
    )
    ChartGameHistoryListItem(item = item, onClick = {})
}
