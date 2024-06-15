package com.yessorae.presentation.ui.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor

// TODO::LATER TickUnit 확장함수로 가독성 향상, 파일이름 적절하게 변경
@Composable
fun provideTickUnitText(tickUnit: TickUnit) =
    when (tickUnit) {
        TickUnit.DAY -> stringResource(id = R.string.common_day)
        TickUnit.HOUR -> stringResource(id = R.string.common_hour)
    }

@Composable
fun TradeType.asText(): String =
    when (this) {
        TradeType.Buy -> stringResource(id = R.string.common_buy)
        TradeType.Sell -> stringResource(id = R.string.common_sell)
    }

fun TradeType.asColor(): Color =
    when (this) {
        TradeType.Buy -> StockUpColor
        TradeType.Sell -> StockDownColor
    }
