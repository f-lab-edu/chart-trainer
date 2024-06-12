package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@Composable
fun TextInfo(
    modifier: Modifier = Modifier,
    keyText: String,
    valueText: String,
    valueColor: Color
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = keyText,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        )

        Text(
            text = valueText,
            style = MaterialTheme.typography.titleMedium,
            color = valueColor
        )
    }
}

@DevicePreviews
@Composable
private fun TextInfoPreview() {
    TextInfo(
        keyText = "keyText",
        valueText = "valueText",
        valueColor = Color.Black
    )
}
