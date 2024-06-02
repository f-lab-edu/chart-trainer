package com.yessorae.presentation.ui.chartgame.component.order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.chartgame.model.PercentageOrderShortCut
import com.yessorae.presentation.ui.chartgame.model.TradeOrderUi
import com.yessorae.presentation.ui.chartgame.model.TradeOrderUiUserAction
import com.yessorae.presentation.ui.designsystem.component.DefaultTextButton
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.theme.TradeTextColor
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@Composable
fun TradeOrderUi(
    modifier: Modifier = Modifier,
    tradeOrderUi: TradeOrderUi
) {
    AnimatedVisibility(
        visible = tradeOrderUi.show(),
        modifier = modifier,
        enter = slideIn { size -> IntOffset(0, size.height) },
        exit = slideOut { size -> IntOffset(0, size.height) }
    ) {
        when (tradeOrderUi) {
            is TradeOrderUi.Buy -> {
                TradeOrder(
                    modifier = Modifier.fillMaxWidth(),
                    tradeColor = StockUpColor,
                    tradeText = stringResource(id = R.string.common_buy),
                    showKeyPad = tradeOrderUi.showKeyPad,
                    maxAvailableStockCount = tradeOrderUi.maxAvailableStockCount,
                    currentStockPrice = tradeOrderUi.currentStockPrice,
                    stockCountInput = tradeOrderUi.stockCountInput,
                    totalBuyingStockPrice = tradeOrderUi.totalBuyingStockPrice,
                    onUserAction = tradeOrderUi.onUserAction
                )
            }

            is TradeOrderUi.Sell -> {
                TradeOrder(
                    modifier = Modifier.fillMaxWidth(),
                    tradeColor = StockDownColor,
                    tradeText = stringResource(id = R.string.common_sell),
                    showKeyPad = tradeOrderUi.showKeyPad,
                    maxAvailableStockCount = tradeOrderUi.maxAvailableStockCount,
                    currentStockPrice = tradeOrderUi.currentStockPrice,
                    stockCountInput = tradeOrderUi.stockCountInput,
                    totalBuyingStockPrice = tradeOrderUi.totalBuyingStockPrice,
                    onUserAction = tradeOrderUi.onUserAction
                )
            }

            else -> {
                // do nothing
            }
        }
    }
}

@Composable
private fun TradeOrder(
    modifier: Modifier,
    tradeColor: Color,
    tradeText: String,
    showKeyPad: Boolean,
    maxAvailableStockCount: Int = 0,
    currentStockPrice: Double = 0.0,
    stockCountInput: String? = null,
    totalBuyingStockPrice: Double,
    onUserAction: (TradeOrderUiUserAction) -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large.copy(
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                        color = tradeColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(tradeText)
                }
                append(stringResource(id = R.string.common_order))
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        val baseStyledTypography = MaterialTheme.typography.bodyMedium
        TradeInfoLine(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.common_orderable_count),
            valueText = buildAnnotatedString {
                append(stringResource(id = R.string.common_order_count))
                withStyle(
                    baseStyledTypography.toSpanStyle().copy(
                        color = tradeColor,
                        fontWeight = FontWeight.Bold
                    ),
                    block = {
                        append(" $maxAvailableStockCount ")
                    }
                )
                append(stringResource(id = R.string.common_stock_unit))
            },
            commonTextStyle = baseStyledTypography
        )

        // input button
        val containerShape = MaterialTheme.shapes.small
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(color = Color.Transparent, shape = containerShape)
                .clip(shape = containerShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = containerShape
                )
                .clickable {
                    onUserAction(TradeOrderUiUserAction.ClickInput)
                }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (stockCountInput.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.trade_order_input_request),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            } else {
                Text(
                    text = stockCountInput,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = stringResource(id = R.string.common_stock_unit),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        // short-cut
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            PercentageOrderShortCut.values().forEach { percentage ->
                ShortCutChip(
                    modifier = Modifier.weight(1f),
                    percentage = percentage.value,
                    onClick = {
                        onUserAction(
                            TradeOrderUiUserAction.ClickRatioShortCut(percentage = percentage)
                        )
                    }
                )
            }
        }

        // trade info
        TradeInfoLine(
            title = stringResource(id = R.string.common_order_price),
            valueText = buildAnnotatedString {
                withStyle(
                    style = baseStyledTypography.toSpanStyle().copy(
                        fontWeight = FontWeight.Bold
                    ),
                    block = {
                        append("$currentStockPrice")
                    }
                )
                append(stringResource(id = R.string.common_money_unit))
            },
            commonTextStyle = baseStyledTypography,
            modifier = Modifier.fillMaxWidth()
        )
        TradeInfoLine(
            title = stringResource(id = R.string.common_total_order_price),
            valueText = buildAnnotatedString {
                withStyle(
                    style = baseStyledTypography.toSpanStyle().copy(
                        fontWeight = FontWeight.Bold
                    ),
                    block = {
                        append("$totalBuyingStockPrice ")
                    }
                )
                append(stringResource(id = R.string.common_money_unit))
            },
            commonTextStyle = baseStyledTypography,
            modifier = Modifier.fillMaxWidth()
        )

        // keypad
        TradeOrderKeyPad(
            show = showKeyPad,
            onClick = { keyPad ->
                onUserAction(
                    TradeOrderUiUserAction.ClickKeyPad(
                        keyPad = keyPad,
                        stockCountInput = stockCountInput
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        // 2-way buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            DefaultTextButton(
                text = stringResource(id = R.string.common_cancel),
                modifier = Modifier.weight(1f),
                onClick = { onUserAction(TradeOrderUiUserAction.ClickCancelButton) },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    contentColor = tradeColor
                )
            )

            DefaultTextButton(
                text = tradeText,
                modifier = Modifier.weight(1f),
                onClick = { onUserAction(TradeOrderUiUserAction.ClickTrade(stockCountInput)) },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = tradeColor,
                    contentColor = TradeTextColor
                )
            )
        }
    }
}

@DevicePreviews
@Composable
fun TradeOrderUiBuyPreview() {
    TradeOrderUi(
        tradeOrderUi = TradeOrderUi.Buy(
            showKeyPad = true,
            maxAvailableStockCount = 1231,
            currentStockPrice = 4893.12,
            stockCountInput = null,
            onUserAction = {}
        )
    )
}

@DevicePreviews
@Composable
fun TradeOrderUiSellPreview() {
    TradeOrderUi(
        tradeOrderUi = TradeOrderUi.Sell(
            showKeyPad = false,
            maxAvailableStockCount = 1231,
            currentStockPrice = 4893.12,
            stockCountInput = "123",
            onUserAction = {}
        )
    )
}
