package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.theme.ChartTrainerTheme
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.designsystem.util.provideTickUnitText
import com.yessorae.presentation.ui.screen.home.model.SettingInfoUi

@Composable
fun SettingInfoUi(
    modifier: Modifier = Modifier,
    settingInfoUi: SettingInfoUi,
    onClickCommissionRate: () -> Unit = {},
    onClickTotalTurn: () -> Unit = {},
    onClickTickUnit: () -> Unit = {}
) {
    val defaultTextColor = MaterialTheme.colorScheme.background

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.home_setting),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = MaterialTheme.shapes.small
                )
                .padding(vertical = 16.dp)
        ) {
            OptionInfo(
                modifier = Modifier
                    .clickable(onClick = onClickCommissionRate)
                    .padding(
                        vertical = 8.dp,
                        horizontal = Dimen.defaultLayoutSidePadding
                    ),
                keyText = stringResource(id = R.string.home_commission_rate),
                valueText = "%.3f".format(settingInfoUi.commissionRate) + "%",
                valueColor = defaultTextColor
            )

            OptionInfo(
                modifier = Modifier
                    .clickable(onClick = onClickTotalTurn)
                    .padding(
                        vertical = 8.dp,
                        horizontal = Dimen.defaultLayoutSidePadding
                    ),
                keyText = stringResource(id = R.string.home_total_turn),
                valueText = "${settingInfoUi.totalTurn}",
                valueColor = defaultTextColor
            )

            OptionInfo(
                modifier = Modifier
                    .clickable(onClick = onClickTickUnit)
                    .padding(
                        vertical = 8.dp,
                        horizontal = Dimen.defaultLayoutSidePadding
                    ),
                keyText = stringResource(id = R.string.home_tick_unit),
                valueText = provideTickUnitText(settingInfoUi.tickUnit),
                valueColor = defaultTextColor
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SettingInfoUiPreview() {
    ChartTrainerTheme {
        SettingInfoUi(
            settingInfoUi = SettingInfoUi(
                commissionRate = 0.3f,
                totalTurn = 100,
                tickUnit = TickUnit.DAY
            )
        )
    }
}
