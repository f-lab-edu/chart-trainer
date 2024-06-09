package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.presentation.ui.designsystem.component.DefaultModalBottomSheet
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.designsystem.util.provideTickUnitText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TickUnitSettingModelBottomSheet(
    tickUnit: TickUnit,
    onDone: (TickUnit) -> Unit,
    onDismissRequest: () -> Unit
) {
    DefaultModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.fillMaxWidth(),
        sheetMaxWidth = LocalConfiguration.current.screenWidthDp.dp,
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp,
        dragHandle = null,
        content = {
            Column(
                modifier = Modifier.padding(vertical = Dimen.defaultLayoutSidePadding)
            ) {
                TickUnit.values().forEach { currentTickUnit ->
                    TickUnitOption(
                        selected = currentTickUnit == tickUnit,
                        text = provideTickUnitText(tickUnit = currentTickUnit),
                        onClick = { onDone(currentTickUnit) }
                    )
                }
            }
        }
    )
}

@Composable
private fun TickUnitOption(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = Dimen.defaultLayoutSidePadding
            )
    ) {
        Icon(
            imageVector = ChartTrainerIcons.Selected,
            contentDescription = null,
            tint = if (selected) {
                StockUpColor
            } else {
                Color.Transparent
            }
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@DevicePreviews
@Composable
private fun TickUnitSettingModelBottomSheetPreview() {
    TickUnitSettingModelBottomSheet(
        tickUnit = TickUnit.DAY,
        onDone = {},
        onDismissRequest = {}
    )
}
