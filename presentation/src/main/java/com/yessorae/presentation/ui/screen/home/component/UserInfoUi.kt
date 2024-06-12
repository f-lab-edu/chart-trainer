package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yessorae.domain.entity.value.Money
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.theme.ChartTrainerTheme
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.screen.home.model.UserInfoUi

@Composable
fun UserInfoUi(
    modifier: Modifier = Modifier,
    userInfoUi: UserInfoUi
) {
    val defaultTextColor = MaterialTheme.colorScheme.background

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.home_my_info),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = MaterialTheme.shapes.small
                )
                .padding(
                    vertical = 16.dp,
                    horizontal = Dimen.defaultLayoutSidePadding
                )
        ) {
            TextInfo(
                modifier = Modifier.padding(bottom = 16.dp),
                keyText = stringResource(id = R.string.home_balance),
                valueText = "%.2f".format(userInfoUi.currentBalance.value) +
                    stringResource(id = R.string.common_money_unit),
                valueColor = defaultTextColor
            )

            TextInfo(
                modifier = Modifier.padding(bottom = 16.dp),
                keyText = stringResource(id = R.string.home_average_rate_of_profit),
                valueText = "%.2f".format(userInfoUi.averageRateOfProfit) + "%",
                valueColor = if (userInfoUi.averageRateOfProfit == 0f) {
                    defaultTextColor
                } else if (userInfoUi.averageRateOfProfit > 0f) {
                    StockUpColor
                } else {
                    StockDownColor
                }
            )

            TextInfo(
                modifier = Modifier.padding(bottom = 4.dp),
                keyText = stringResource(id = R.string.home_record_and_rate_of_winning),
                valueText = "%.2f".format(userInfoUi.rateOfWinning * 100) + "%",
                valueColor = defaultTextColor
            )
            if (userInfoUi.showWinningRateBar) {
                WinLoseRatioBar(
                    modifier = Modifier.fillMaxWidth(),
                    winCount = userInfoUi.winCount,
                    loseCount = userInfoUi.loseCount,
                    rateOfWinning = userInfoUi.rateOfWinning,
                    rateOfLosing = userInfoUi.rateOfLosing
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun UserInfoUiPreview() {
    ChartTrainerTheme {
        UserInfoUi(
            userInfoUi = UserInfoUi(
                currentBalance = Money(1000.0),
                winCount = 10,
                loseCount = 5,
                averageRateOfProfit = 0.5f
            )
        )
    }
}
