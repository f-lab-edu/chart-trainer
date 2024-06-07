package com.yessorae.presentation.ui.screen.chartgame.component

import android.graphics.Typeface
import android.text.Layout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEndAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberCandlestickCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shape.markerCornered
import com.patrykandpatrick.vico.core.cartesian.AutoScrollCondition
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasureContext
import com.patrykandpatrick.vico.core.cartesian.HorizontalDimensions
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.Insets
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.axis.AxisPosition
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CandlestickCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.RandomCartesianModelGenerator.getRandomCandlestickLayerModelPartial
import com.patrykandpatrick.vico.core.cartesian.data.candlestickSeries
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.copyColor
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.Shape
import com.yessorae.presentation.ui.screen.chartgame.model.CandleStickChartUi
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.designsystem.util.UiConstants.SHADOW_DY_DP
import com.yessorae.presentation.ui.designsystem.util.UiConstants.SHADOW_RADIUS_DP
import com.yessorae.presentation.ui.designsystem.util.UiConstants.SHADOW_RADIUS_MULTIPLIER

@Composable
fun CandleChartUi(
    modifier: Modifier = Modifier,
    candleStickChart: CandleStickChartUi
) {
    val modelProducer = remember { CartesianChartModelProducer.build() }
    val scrollState = rememberVicoScrollState(
        initialScroll = Scroll.Absolute.End,
        autoScrollCondition = AutoScrollCondition.OnModelSizeIncreased
    )
    val zoomState = rememberVicoZoomState()
    val marker = rememberMarker(showIndicator = false)

    LaunchedEffect(key1 = candleStickChart) {
        modelProducer.tryRunTransaction {
            if (candleStickChart.opening.isEmpty().not()) {
                candlestickSeries(
                    opening = candleStickChart.opening,
                    closing = candleStickChart.closing,
                    low = candleStickChart.low,
                    high = candleStickChart.high
                )
            }
        }
    }

    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberCandlestickCartesianLayer(
                minCandleBodyHeight = 2.dp,
                candleSpacing = 4.dp,
                scaleCandleWicks = true,
                verticalAxisPosition = AxisPosition.Vertical.Start,
                axisValueOverrider = remember {
                    AxisValueOverrider.adaptiveYValues(1.01f)
                }
            ),
            endAxis = rememberEndAxis(guideline = null)
        ),
        scrollState = scrollState,
        zoomState = zoomState,
        modelProducer = modelProducer,
        marker = marker,
        modifier = modifier,
        horizontalLayout = HorizontalLayout.Segmented
    )
}

@Composable
private fun rememberMarker(
    labelPosition: DefaultCartesianMarker.LabelPosition = DefaultCartesianMarker.LabelPosition.Top,
    showIndicator: Boolean = true
): CartesianMarker {
    val labelBackgroundShape = Shape.markerCornered(Corner.FullyRounded)

    val labelBackground = rememberShapeComponent(
        labelBackgroundShape,
        MaterialTheme.colorScheme.surface
    ).setShadow(
        radius = SHADOW_RADIUS_DP,
        dy = SHADOW_DY_DP,
        applyElevationOverlay = true
    )

    val label = rememberTextComponent(
        color = MaterialTheme.colorScheme.onSurface,
        background = labelBackground,
        padding = Dimensions.of(8.dp, 4.dp),
        typeface = Typeface.MONOSPACE,
        textAlignment = Layout.Alignment.ALIGN_CENTER,
        minWidth = TextComponent.MinWidth.fixed(40.dp)
    )

    val indicatorFrontComponent = rememberShapeComponent(
        Shape.Pill,
        MaterialTheme.colorScheme.surface
    )

    val indicatorCenterComponent = rememberShapeComponent(Shape.Pill)

    val indicatorRearComponent = rememberShapeComponent(Shape.Pill)

    val indicator = rememberLayeredComponent(
        rear = indicatorRearComponent,
        front = rememberLayeredComponent(
            rear = indicatorCenterComponent,
            front = indicatorFrontComponent,
            padding = Dimensions.of(5.dp)
        ),
        padding = Dimensions.of(10.dp)
    )

    val guideline = rememberAxisGuidelineComponent()

    return remember(
        label,
        labelPosition,
        indicator,
        showIndicator,
        guideline
    ) {
        object : DefaultCartesianMarker(
            label = label,
            labelPosition = labelPosition,
            indicator = if (showIndicator) {
                indicator
            } else {
                null
            },
            indicatorSizeDp = 36f,
            setIndicatorColor = if (showIndicator) {
                { color ->
                    indicatorRearComponent.color = color.copyColor(alpha = .15f)
                    indicatorCenterComponent.color = color
                    indicatorCenterComponent.setShadow(radius = 12f, color = color)
                }
            } else {
                null
            },
            guideline = guideline
        ) {
            override fun getInsets(
                context: CartesianMeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions
            ) {
                with(context) {
                    outInsets.top =
                        (SHADOW_RADIUS_MULTIPLIER * SHADOW_RADIUS_DP - SHADOW_DY_DP).pixels
                    if (labelPosition == LabelPosition.AroundPoint) return
                    outInsets.top +=
                        label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun CandleChartUiPreview() {
    val partial =
        getRandomCandlestickLayerModelPartial().complete() as CandlestickCartesianLayerModel
    val series = partial.series
    CandleChartUi(
        modifier = Modifier.fillMaxWidth(),
        candleStickChart = CandleStickChartUi(
            opening = series.map { it.opening.toDouble() },
            closing = series.map { it.closing.toDouble() },
            low = series.map { it.low.toDouble() },
            high = series.map { it.high.toDouble() }
        )
    )
}
