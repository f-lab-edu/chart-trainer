package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@Composable
fun OptionInfo(
    modifier: Modifier = Modifier,
    keyText: String,
    valueText: String,
    valueColor: Color
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextInfo(
            keyText = keyText,
            valueText = valueText,
            valueColor = valueColor
        )

        Icon(
            imageVector = ChartTrainerIcons.OptionArrow,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    }
}

@DevicePreviews
@Composable
private fun OptionInfoPreview() {
    OptionInfo(
        keyText = "keyText",
        valueText = "valueText",
        valueColor = Color.Black
    )
}
