package com.yessorae.presentation.ui.chartgame.model

sealed interface ChartGameScreenUserAction {
    object ClickNewChartButtonScreen : ChartGameScreenUserAction
    object ClickChartGameScreenHistoryButton : ChartGameScreenUserAction
    object ClickQuitGameButtonScreen : ChartGameScreenUserAction
    object ClickBuyButton : ChartGameScreenUserAction
    object ClickSellButton : ChartGameScreenUserAction
    object ClickNextTickButton : ChartGameScreenUserAction
}

sealed interface BuyingOrderUiUserAction {
    object ClickInput : BuyingOrderUiUserAction
    object ClickSellButton : BuyingOrderUiUserAction
    object ClickCancelButton : BuyingOrderUiUserAction
    object DoSystemBack : BuyingOrderUiUserAction
    data class ClickKeyPad(
        val keyPad: TradeOrderKeyPad
    ) : BuyingOrderUiUserAction

    data class ClickPercentageShortCut(
        val percent: Int
    ) : BuyingOrderUiUserAction
}

sealed interface SellingOrderUiUserAction {
    object ClickInput : SellingOrderUiUserAction
    object ClickSellingButton : SellingOrderUiUserAction
    object ClickCancelButton : SellingOrderUiUserAction
    object DoSystemBack : SellingOrderUiUserAction
    data class ClickPercentageShortCut(
        val percent: Int
    ) : SellingOrderUiUserAction

    data class ClickKeyPad(
        val keyPad: TradeOrderKeyPad
    ) : SellingOrderUiUserAction
}
