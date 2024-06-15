package com.yessorae.presentation.ui.screen.tradehistory.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import kotlin.math.min

@Composable
fun CircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    progressColor: Color = Color.Blue,
    strokeWidth: Float = 20f
) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val radius = (min(canvasWidth, canvasHeight) / 2) - strokeWidth / 2
        val center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)

        // Draw background circle
        drawCircle(
            color = backgroundColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        // Draw progress circle
        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth)
        )
    }
}

@DevicePreviews
@Composable
fun PreviewCircularProgressBar() {
    CircularProgressBar(
        progress = 0.6789f,
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.LightGray,
        progressColor = Color.Blue,
        strokeWidth = 40f
    )
}
