package com.yessorae.presentation.ui.chartgame.model

sealed interface ChartGameScreenUserAction {
    object ClickNewChartButton : ChartGameScreenUserAction
    object ClickChartGameScreenHistoryButton : ChartGameScreenUserAction
    object ClickQuitGameButton : ChartGameScreenUserAction
    object ClickBuyButton : ChartGameScreenUserAction
    object ClickSellButton : ChartGameScreenUserAction
    object ClickNextTickButton : ChartGameScreenUserAction
}

sealed interface TradeOrderUiUserAction {
    object ClickInput : TradeOrderUiUserAction

    data class ClickTrade(
        val stockCountInput: String?
    ) : TradeOrderUiUserAction

    object ClickCancelButton : TradeOrderUiUserAction

    object DoSystemBack : TradeOrderUiUserAction

    data class ClickKeyPad(
        val keyPad: TradeOrderKeyPad,
        val stockCountInput: String?
    ) : TradeOrderUiUserAction

    data class ClickRatioShortCut(
        val percentage: PercentageOrderShortCut
    ) : TradeOrderUiUserAction
}
