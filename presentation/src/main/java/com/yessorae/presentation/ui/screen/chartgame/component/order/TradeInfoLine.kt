package com.yessorae.presentation.ui.screen.chartgame.component.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@Composable
fun TradeInfoLine(
    title: String,
    valueText: AnnotatedString,
    commonTextStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            style = commonTextStyle,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = valueText,
            style = commonTextStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@DevicePreviews
@Composable
private fun TradeInfoLinePreview() {
    MaterialTheme {
        TradeInfoLine(
            title = "주문가능",
            valueText = buildAnnotatedString {
                withStyle(
                    MaterialTheme.typography.labelLarge.toSpanStyle().copy(
                        fontWeight = FontWeight.Bold
                    ),
                    block = {
                        append(" 100000000000000000 ")
                    }
                )
                append("주")
            },
            commonTextStyle = MaterialTheme.typography.labelMedium
        )
    }
}
