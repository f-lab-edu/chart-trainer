package com.yessorae.presentation.ui.screen.tradehistory.model

sealed interface TradeHistoryScreenEvent {
    object NavigateBack : TradeHistoryScreenEvent
    object UnknownError : TradeHistoryScreenEvent
}
