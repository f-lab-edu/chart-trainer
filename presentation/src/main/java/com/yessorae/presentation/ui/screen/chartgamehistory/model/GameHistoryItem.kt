package com.yessorae.presentation.ui.screen.chartgamehistory.model

import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.value.Money
import java.time.LocalDateTime

data class GameHistoryItem(
    val id: Long,
    val ticker: String,
    val totalTurn: Int,
    val tickUnit: TickUnit,
    val totalProfit: Money,
    val isTotalProfitPositive: Boolean,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?
)
