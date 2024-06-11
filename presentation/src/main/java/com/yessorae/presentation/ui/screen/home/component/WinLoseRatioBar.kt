package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.theme.StockDownBackgroundColor
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpBackgroundColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@Composable
fun WinLoseRatioBar(
    modifier: Modifier,
    winCount: Int,
    loseCount: Int,
    rateOfWinning: Float,
    rateOfLosing: Float
) {
    Row(
        modifier = modifier.clip(shape = MaterialTheme.shapes.medium)
    ) {
        Box(
            modifier = Modifier
                .weight(rateOfWinning)
                .background(color = StockUpBackgroundColor),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "$winCount" + stringResource(id = R.string.home_win),
                style = MaterialTheme.typography.labelSmall,
                color = StockUpColor,
                modifier = Modifier
                    .padding(8.dp)
            )
        }

        Box(
            modifier = Modifier
                .weight(rateOfLosing)
                .background(color = StockDownBackgroundColor),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "$loseCount" + stringResource(id = R.string.home_lose),
                style = MaterialTheme.typography.labelSmall,
                color = StockDownColor,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@DevicePreviews
@Composable
private fun WinLoseRatioBarPreview() {
    WinLoseRatioBar(
        modifier = Modifier,
        winCount = 10,
        loseCount = 5,
        rateOfWinning = 0.7f,
        rateOfLosing = 0.3f
    )
}
