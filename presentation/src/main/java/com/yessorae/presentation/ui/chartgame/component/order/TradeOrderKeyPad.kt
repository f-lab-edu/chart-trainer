package com.yessorae.presentation.ui.chartgame.component.order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.chartgame.model.TradeOrderKeyPad
import com.yessorae.presentation.ui.designsystem.component.HorizontalGrid
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@Composable
fun TradeOrderKeyPad(
    modifier: Modifier = Modifier,
    show: Boolean,
    onClick: (TradeOrderKeyPad) -> Unit
) {
    AnimatedVisibility(visible = show) {
        HorizontalGrid(
            modifier = modifier.background(color = MaterialTheme.colorScheme.background),
            columns = 3
        ) {
            val contentColor = MaterialTheme.colorScheme.onBackground
            val textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)

            (1..9).forEach { number ->
                KeyPadItem(
                    onClick = { onClick(TradeOrderKeyPad.Number("0")) },
                    content = {
                        Text(
                            text = "$number",
                            style = textStyle,
                            color = contentColor
                        )
                    }
                )
            }

            KeyPadItem(
                onClick = { onClick(TradeOrderKeyPad.Number("0")) },
                content = {
                    Text(
                        text = stringResource(id = R.string.trade_order_delete_all),
                        style = textStyle,
                        color = contentColor
                    )
                }
            )

            KeyPadItem(
                onClick = { onClick(TradeOrderKeyPad.Number("0")) },
                content = {
                    Text(
                        text = "0",
                        style = textStyle,
                        color = contentColor
                    )
                }
            )

            KeyPadItem(
                onClick = { onClick(TradeOrderKeyPad.Delete) },
                content = {
                    Icon(
                        imageVector = ChartTrainerIcons.Delete,
                        contentDescription = null,
                        tint = contentColor
                    )
                }
            )
        }
    }
}

@Composable
private fun KeyPadItem(
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@DevicePreviews
@Composable
fun TradeOrderKeyPadPreview() {
    MaterialTheme {
        TradeOrderKeyPad(
            modifier = Modifier.fillMaxWidth(),
            show = true,
            onClick = {}
        )
    }
}
