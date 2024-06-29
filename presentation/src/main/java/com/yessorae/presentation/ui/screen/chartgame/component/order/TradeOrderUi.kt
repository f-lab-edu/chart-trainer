package com.yessorae.presentation.ui.screen.chartgame.component.order

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.DefaultModalBottomSheet
import com.yessorae.presentation.ui.designsystem.component.DefaultTextButton
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.theme.TradeTextColor
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.screen.chartgame.model.BuyingOrderUiUserAction
import com.yessorae.presentation.ui.screen.chartgame.model.PercentageOrderShortCut
import com.yessorae.presentation.ui.screen.chartgame.model.SellingOrderUiUserAction
import com.yessorae.presentation.ui.screen.chartgame.model.TradeOrderKeyPad
import com.yessorae.presentation.ui.screen.chartgame.model.TradeOrderUi

@Composable
fun TradeOrderUi(
    tradeOrderUi: TradeOrderUi,
    modifier: Modifier = Modifier,
    onBuyingUserAction: (BuyingOrderUiUserAction) -> Unit = {},
    onSellingUserAction: (SellingOrderUiUserAction) -> Unit = {}
) {
    if (tradeOrderUi.show()) {
        when (tradeOrderUi) {
            is TradeOrderUi.Buy -> {
                TradeOrderBottomSheet(
                    modifier = modifier.fillMaxWidth(),
                    tradeColor = StockUpColor,
                    tradeText = stringResource(id = R.string.common_buy),
                    showKeyPad = tradeOrderUi.showKeyPad,
                    maxAvailableStockCount = tradeOrderUi.maxAvailableStockCount,
                    currentStockPrice = tradeOrderUi.currentStockPrice,
                    stockCountInput = tradeOrderUi.stockCountInput,
                    totalBuyingStockPrice = tradeOrderUi.totalBuyingStockPrice,
                    onDismissRequest = {
                        onBuyingUserAction(
                            BuyingOrderUiUserAction.DoSystemBack
                        )
                    },
                    onClickShowKeyPad = {
                        onBuyingUserAction(
                            BuyingOrderUiUserAction.ClickShowKeyPad
                        )
                    },
                    onClickKeypad = { tradeOrderKeyPad, stockCountInput ->
                        onBuyingUserAction(
                            BuyingOrderUiUserAction.ClickKeyPad(
                                keyPad = tradeOrderKeyPad,
                                stockCountInput = stockCountInput,
                                maxAvailableStockCount =
                                tradeOrderUi.clickData.maxAvailableStockCount,
                                ownedStockCount = tradeOrderUi.clickData.ownedStockCount
                            )
                        )
                    },
                    onClickRatioShortCut = { percentage ->
                        onBuyingUserAction(
                            BuyingOrderUiUserAction.ClickRatioShortCut(
                                percentage = percentage,
                                maxAvailableStockCount =
                                tradeOrderUi.clickData.maxAvailableStockCount,
                                ownedStockCount = tradeOrderUi.clickData.ownedStockCount
                            )
                        )
                    },
                    onClickCancelButton = {
                        onBuyingUserAction(
                            BuyingOrderUiUserAction.ClickCancelButton
                        )
                    },
                    onClickTrade = { stockCountInput ->
                        onBuyingUserAction(
                            with(tradeOrderUi.clickData) {
                                BuyingOrderUiUserAction.ClickTrade(
                                    stockCountInput = stockCountInput,
                                    gameId = gameId,
                                    ownedStockCount = ownedStockCount,
                                    ownedAverageStockPrice = ownedAverageStockPrice,
                                    currentStockPrice = currentStockPrice,
                                    currentTurn = currentTurn
                                )
                            }
                        )
                    }
                )
            }

            is TradeOrderUi.Sell -> {
                TradeOrderBottomSheet(
                    modifier = modifier.fillMaxWidth(),
                    tradeColor = StockDownColor,
                    tradeText = stringResource(id = R.string.common_sell),
                    showKeyPad = tradeOrderUi.showKeyPad,
                    maxAvailableStockCount = tradeOrderUi.maxAvailableStockCount,
                    currentStockPrice = tradeOrderUi.currentStockPrice,
                    stockCountInput = tradeOrderUi.stockCountInput,
                    totalBuyingStockPrice = tradeOrderUi.totalBuyingStockPrice,
                    onDismissRequest = {
                        onSellingUserAction(
                            SellingOrderUiUserAction.DoSystemBack
                        )
                    },
                    onClickShowKeyPad = {
                        onSellingUserAction(
                            SellingOrderUiUserAction.ClickShowKeyPad
                        )
                    },
                    onClickKeypad = { tradeOrderKeyPad, stockCountInput ->
                        onSellingUserAction(
                            SellingOrderUiUserAction.ClickKeyPad(
                                keyPad = tradeOrderKeyPad,
                                stockCountInput = stockCountInput,
                                ownedStockCount = tradeOrderUi.clickData.ownedStockCount
                            )
                        )
                    },
                    onClickRatioShortCut = { percentage ->
                        onSellingUserAction(
                            SellingOrderUiUserAction.ClickRatioShortCut(
                                percentage = percentage,
                                ownedStockCount = tradeOrderUi.clickData.ownedStockCount
                            )
                        )
                    },
                    onClickCancelButton = {
                        onSellingUserAction(
                            SellingOrderUiUserAction.ClickCancelButton
                        )
                    },
                    onClickTrade = { stockCountInput ->
                        onSellingUserAction(
                            with(tradeOrderUi.clickData) {
                                SellingOrderUiUserAction.ClickTrade(
                                    stockCountInput = stockCountInput,
                                    gameId = gameId,
                                    ownedStockCount = ownedStockCount,
                                    ownedAverageStockPrice = ownedAverageStockPrice,
                                    currentStockPrice = currentStockPrice,
                                    currentTurn = currentTurn
                                )
                            }
                        )
                    }
                )
            }

            else -> {
                // do nothing
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TradeOrderBottomSheet(
    modifier: Modifier = Modifier,
    tradeColor: Color,
    tradeText: String,
    showKeyPad: Boolean,
    maxAvailableStockCount: Int = 0,
    currentStockPrice: Double = 0.0,
    stockCountInput: String,
    totalBuyingStockPrice: Double,
    onDismissRequest: () -> Unit,
    onClickShowKeyPad: () -> Unit = {},
    onClickKeypad: (TradeOrderKeyPad, String) -> Unit = { _, _ -> },
    onClickRatioShortCut: (PercentageOrderShortCut) -> Unit = {},
    onClickCancelButton: () -> Unit,
    onClickTrade: (String) -> Unit
) {
    DefaultModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { false }
        ),
        sheetMaxWidth = LocalConfiguration.current.screenWidthDp.dp,
        shape = MaterialTheme.shapes.large,
        containerColor = Color.Transparent,
        tonalElevation = 10.dp,
        dragHandle = null,
        content = {
            TradeOrder(
                modifier = modifier,
                tradeColor = tradeColor,
                tradeText = tradeText,
                showKeyPad = showKeyPad,
                maxAvailableStockCount = maxAvailableStockCount,
                currentStockPrice = currentStockPrice,
                stockCountInput = stockCountInput,
                totalBuyingStockPrice = totalBuyingStockPrice,
                onClickShowKeyPad = onClickShowKeyPad,
                onClickKeypad = onClickKeypad,
                onClickRatioShortCut = onClickRatioShortCut,
                onClickCancelButton = onClickCancelButton,
                onClickTrade = onClickTrade
            )
        }
    )
}

@Composable
private fun TradeOrder(
    modifier: Modifier,
    tradeColor: Color,
    tradeText: String,
    showKeyPad: Boolean,
    maxAvailableStockCount: Int = 0,
    currentStockPrice: Double = 0.0,
    stockCountInput: String = "",
    totalBuyingStockPrice: Double,
    onClickShowKeyPad: () -> Unit = {},
    onClickKeypad: (TradeOrderKeyPad, String) -> Unit = { _, _ -> },
    onClickRatioShortCut: (PercentageOrderShortCut) -> Unit = {},
    onClickCancelButton: () -> Unit,
    onClickTrade: (String) -> Unit
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
                .clickable(
                    onClick = onClickShowKeyPad
                )
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (stockCountInput.isEmpty()) {
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
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            PercentageOrderShortCut.values().forEach { percentage ->
                ShortCutChip(
                    modifier = Modifier.weight(1f),
                    percentage = percentage.value,
                    onClick = {
                        onClickRatioShortCut(percentage)
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
                        append("%.2f".format(currentStockPrice))
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
                        append("%.2f".format(totalBuyingStockPrice))
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
                onClickKeypad(
                    keyPad,
                    stockCountInput
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
                onClick = onClickCancelButton,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    contentColor = tradeColor
                ),
                shape = MaterialTheme.shapes.small
            )

            DefaultTextButton(
                text = tradeText,
                modifier = Modifier.weight(1f),
                onClick = {
                    onClickTrade(stockCountInput)
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = tradeColor,
                    contentColor = TradeTextColor
                ),
                shape = MaterialTheme.shapes.small
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
            stockCountInput = ""
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
            stockCountInput = "123"
        )
    )
}
