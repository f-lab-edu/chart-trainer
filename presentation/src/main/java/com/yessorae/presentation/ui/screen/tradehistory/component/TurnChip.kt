package com.yessorae.presentation.ui.screen.tradehistory.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun TurnChip(
    modifier: Modifier = Modifier,
    currentTurn: Int,
    totalTurn: Int,
    color: Color
) {
    var textHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressBar(
            progress = currentTurn.toFloat() / totalTurn,
            modifier = Modifier.size(textHeight),
            progressColor = color,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
            strokeWidth = 10f
        )

        Text(
            text = "$currentTurn/$totalTurn",
            style = MaterialTheme.typography.labelLarge,
            onTextLayout = {
                textHeight = with(density) { it.size.height.toDp() }
            }
        )
    }
}
