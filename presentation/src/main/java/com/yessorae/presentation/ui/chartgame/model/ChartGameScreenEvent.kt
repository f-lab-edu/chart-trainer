package com.yessorae.presentation.ui.chartgame.model

sealed interface ChartGameScreenEvent {
    data class Toast(val text: String) : ChartGameScreenEvent
}