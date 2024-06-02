package com.yessorae.presentation.ui.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalGrid(
    modifier: Modifier = Modifier,
    columns: Int,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val cellWidth = constraints.maxWidth / columns
        val cellConstraints = Constraints.fixedWidth(cellWidth)

        val placeables = measurables.map { it.measure(cellConstraints) }

        val rows = (placeables.size + columns - 1) / columns

        val width = constraints.maxWidth
        val height = (placeables.firstOrNull()?.height ?: 0) * rows

        layout(width, height) {
            var xPosition = 0
            var yPosition = 0

            placeables.forEachIndexed { index, placeable ->
                if (index % columns == 0) {
                    xPosition = 0
                    if (index != 0) {
                        yPosition += placeable.height
                    }
                }
                placeable.placeRelative(x = xPosition, y = yPosition)
                xPosition += placeable.width
            }
        }
    }
}

@Preview
@Composable
fun MyGridExample() {
    HorizontalGrid(columns = 3) {
        for (i in 1..9) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .background(Color.Gray)
            )
        }
    }
}
