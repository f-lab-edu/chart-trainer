package com.yessorae.presentation.ui.screen.tradehistory.model

sealed interface TradeHistoryScreenEvent {
    object NavigateToBack : TradeHistoryScreenEvent
    object UnknownError : TradeHistoryScreenEvent
}
