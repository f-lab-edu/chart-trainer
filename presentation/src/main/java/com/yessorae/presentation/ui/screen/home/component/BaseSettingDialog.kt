package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.DefaultTextButton
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.theme.TradeTextColor

@Composable
fun BaseSettingDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onDone: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(width = 300.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(Dimen.defaultLayoutSidePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            content()

            ChartTrainerSettingTwoWayButtons(
                onCancel = onDismissRequest,
                onDone = onDone
            )
        }
    }
}

@Composable
fun ChartTrainerSettingTwoWayButtons(
    onCancel: () -> Unit,
    onDone: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        DefaultTextButton(
            text = stringResource(id = R.string.common_cancel),
            onClick = onCancel,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = StockUpColor
            ),
            shape = MaterialTheme.shapes.small
        )
        DefaultTextButton(
            text = stringResource(id = R.string.home_done),
            onClick = onDone,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.textButtonColors(
                containerColor = StockUpColor,
                contentColor = TradeTextColor
            ),
            shape = MaterialTheme.shapes.small
        )
    }
}
