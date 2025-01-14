package com.yessorae.presentation.ui.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.trade.TradeType
import com.yessorae.domain.entity.value.Money
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.theme.StockDownColor
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.absoluteValue

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
        TradeType.BUY -> stringResource(id = R.string.common_buy)
        TradeType.SELL -> stringResource(id = R.string.common_sell)
    }

fun TradeType.asColor(): Color =
    when (this) {
        TradeType.BUY -> StockUpColor
        TradeType.SELL -> StockDownColor
    }

fun Money.asDefaultDisplayText(): String = "%.2f".format(value)

fun Money.asDisplayTotalProfit(): String = (if (value > 0.0) "+" else "-") + "%.2f".format(value.absoluteValue)

fun Double.asSignedDisplayText(): String = (if (this > 0f) "+" else "-") + "%.2f".format(this.absoluteValue)

fun getDisplayDateRangeText(
    startDate: LocalDateTime?,
    endDate: LocalDateTime?
): String {
    val formatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT)
        .localizedBy(Locale.getDefault())

    return "${startDate?.format(formatter) ?: ""} - ${endDate?.format(formatter) ?: ""}"
}
