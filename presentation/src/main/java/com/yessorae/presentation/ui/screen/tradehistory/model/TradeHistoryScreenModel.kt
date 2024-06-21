package com.yessorae.presentation.ui.screen.tradehistory.model

data class TradeHistoryScreenModel(
    val totalTurn: Int = 0,
    val tradeHistories: List<TradeHistoryListItem> = listOf(),
    val loading: Boolean = true,
    val none: Boolean = false
)
