package com.yessorae.presentation.ui.chartgame.model

import com.yessorae.domain.entity.tick.Tick

data class ChartGameScreenState(
    val currentTurn: String = "",
    val totalTurn: String = "",
    val gameProgress: Float = 0f,
    val showLoading: Boolean = false,
    //아래와 같이 라이브러리에 맞춘 형태로 지양하는 UI 모델 형태이다. 변경 고민중.
    val transactionVolume: List<Double> = listOf(),
    val candleStickChart: CandleStickChartUi = CandleStickChartUi(),
    val buyingOrderUi: BuyingOrderUi? = null,
    val sellingOrderUi: SellingOrderUi? = null,
    val onUserAction: (ChartGameScreenUserAction) -> Unit = {}
)

data class CandleStickChartUi(
    val opening: List<Double> = listOf(),
    val closing: List<Double> = listOf(),
    val low: List<Double> = listOf(),
    val high: List<Double> = listOf(),
)

data class BuyingOrderUi(
    val show: Boolean = false,
    val showKeyPad: Boolean = false,
    val maxAvailableStockCount: Int = 0,
    val currentStockPrice: Double = 0.0,
    val stockCountInput: String? = null,
    val onUserAction: (BuyingOrderUiUserAction) -> Unit = {}
) {
    val totalBuyingStockPrice: Double = currentStockPrice * (stockCountInput?.toInt() ?: 0)
}

data class SellingOrderUi(
    val show: Boolean = false,
    val showKeyPad: Boolean = false,
    val maxAvailableStockCount: Int = 0,
    val currentStockPrice: Double = 0.0,
    val stockCountInput: String? = null,
    val onUserAction: (SellingOrderUiUserAction) -> Unit = {}
) {
    val totalSellingStockPrice: Double = currentStockPrice * (stockCountInput?.toInt() ?: 0)
}

fun List<Tick>.asTransactionVolume(): List<Double> = this.map { tick ->
    tick.transactionCount.toDouble()
}

fun List<Tick>.asCandleStickChartUiState(): CandleStickChartUi = CandleStickChartUi(
    opening = this.map { tick -> tick.openPrice.value },
    closing = this.map { tick -> tick.closePrice.value },
    low = this.map { tick -> tick.minPrice.value },
    high = this.map { tick -> tick.maxPrice.value },
)
