package com.yessorae.presentation.ui.screen.chartgame.model

import com.yessorae.domain.entity.value.Money

sealed interface ChartGameScreenUserAction {
    data class ClickNewChartButton(
        val gameId: Long
    ) : ChartGameScreenUserAction

    data class ClickChartGameScreenHistoryButton(
        val gameId: Long
    ) : ChartGameScreenUserAction

    data class ClickQuitGameButton(
        val gameId: Long
    ) : ChartGameScreenUserAction

    data class ClickBuyButton(
        val gameId: Long,
        val ownedStockCount: Int,
        val ownedAverageStockPrice: Money,
        val currentBalance: Money,
        val currentStockPrice: Money,
        val currentTurn: Int
    ) : ChartGameScreenUserAction

    data class ClickSellButton(
        val gameId: Long,
        val ownedAverageStockPrice: Money,
        val currentStockPrice: Money,
        val currentTurn: Int,
        val ownedStockCount: Int
    ) : ChartGameScreenUserAction

    data class ClickNextTickButton(
        val gameId: Long
    ) : ChartGameScreenUserAction
}

sealed interface BuyingOrderUiUserAction {
    object ClickShowKeyPad : BuyingOrderUiUserAction

    data class ClickTrade(
        val stockCountInput: String?,
        val gameId: Long,
        val ownedStockCount: Int,
        val ownedAverageStockPrice: Money,
        val currentStockPrice: Money,
        val currentTurn: Int
    ) : BuyingOrderUiUserAction

    object ClickCancelButton : BuyingOrderUiUserAction

    object DoSystemBack : BuyingOrderUiUserAction

    data class ClickKeyPad(
        val keyPad: TradeOrderKeyPad,
        val stockCountInput: String,
        val maxAvailableStockCount: Int
    ) : BuyingOrderUiUserAction

    data class ClickRatioShortCut(
        val percentage: PercentageOrderShortCut,
        val maxAvailableStockCount: Int
    ) : BuyingOrderUiUserAction
}

sealed interface SellingOrderUiUserAction {
    object ClickShowKeyPad : SellingOrderUiUserAction

    data class ClickTrade(
        val stockCountInput: String,
        val gameId: Long,
        val ownedStockCount: Int,
        val ownedAverageStockPrice: Money,
        val currentStockPrice: Money,
        val currentTurn: Int
    ) : SellingOrderUiUserAction

    object ClickCancelButton : SellingOrderUiUserAction

    object DoSystemBack : SellingOrderUiUserAction

    data class ClickKeyPad(
        val keyPad: TradeOrderKeyPad,
        val stockCountInput: String,
        val ownedStockCount: Int
    ) : SellingOrderUiUserAction

    data class ClickRatioShortCut(
        val percentage: PercentageOrderShortCut,
        val ownedStockCount: Int
    ) : SellingOrderUiUserAction
}
