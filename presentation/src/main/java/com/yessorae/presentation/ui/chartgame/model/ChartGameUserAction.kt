package com.yessorae.presentation.ui.chartgame.model

sealed interface ChartGameUserAction {
    object ClickNewChartButton : ChartGameUserAction
    object ClickChartGameHistoryButton : ChartGameUserAction
    object ClickQuitGameButton : ChartGameUserAction
    object ClickStartGameButton : ChartGameUserAction
    object ClickBuyButton : ChartGameUserAction
    object ClickSellButton : ChartGameUserAction
    object ClickNextTickButton : ChartGameUserAction
}