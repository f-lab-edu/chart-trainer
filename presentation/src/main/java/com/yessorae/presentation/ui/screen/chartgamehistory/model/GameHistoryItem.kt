package com.yessorae.presentation.ui.screen.chartgamehistory.model

import com.yessorae.domain.entity.tick.TickUnit

data class GameHistoryItem(
    val id: Long,
    val ticker: String,
    val totalTurn: Int,
    val tickUnit: TickUnit,
    val totalProfit: String,
    val isTotalProfitPositive: Boolean,
    val time: String = ""
)
