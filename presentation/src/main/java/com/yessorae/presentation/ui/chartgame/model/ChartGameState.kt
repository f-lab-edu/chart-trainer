package com.yessorae.presentation.ui.chartgame.model

import com.yessorae.domain.common.DefaultValues.defaultTickUnit
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit

data class ChartGameScreenState(
    val currentTurn: Int = 0,
    val totalTurn: Int = 0,
    val totalProfit: Double = 0.0,
    val rateOfProfit: Double = 0.0,
    val gameProgress: Float = 0f,
    val showLoading: Boolean = false,
    val tickUnit: TickUnit = defaultTickUnit,
    // 아래와 같이 라이브러리에 맞춘 형태로 지양하는 UI 모델 형태이다. 변경 고민중.
    val transactionVolume: List<Double> = listOf(),
    val candleStickChart: CandleStickChartUi = CandleStickChartUi(),
    val tradeOrderUi: TradeOrderUi = TradeOrderUi.Hide,
    val onUserAction: (ChartGameScreenUserAction) -> Unit = {}
) {
    val isStart = totalTurn > 0
    val enabledBuyButton: Boolean = currentTurn != totalTurn
    val enabledSellButton: Boolean = currentTurn == 0
    val enabledNextTurnButton: Boolean = currentTurn != totalTurn
}

data class CandleStickChartUi(
    val opening: List<Double> = listOf(),
    val closing: List<Double> = listOf(),
    val low: List<Double> = listOf(),
    val high: List<Double> = listOf()
) {
    private val isEmpty: Boolean =
        opening.isEmpty() || closing.isEmpty() || low.isEmpty() || high.isEmpty()
    val displayable: Boolean = isEmpty.not() &&
        opening.size == closing.size &&
        closing.size == low.size &&
        low.size == high.size
}

sealed class TradeOrderUi {
    data class Buy(
        val showKeyPad: Boolean = false,
        val maxAvailableStockCount: Int = 0,
        val currentStockPrice: Double = 0.0,
        val stockCountInput: String? = null,
        val onUserAction: (TradeOrderUiUserAction) -> Unit = {}
    ) : TradeOrderUi() {
        val totalBuyingStockPrice: Double by lazy {
            getTotalBuyingStockPrice(
                currentStockPrice = currentStockPrice,
                stockCountInput = stockCountInput
            )
        }
    }

    data class Sell(
        val showKeyPad: Boolean = false,
        val maxAvailableStockCount: Int = 0,
        val currentStockPrice: Double = 0.0,
        val stockCountInput: String? = null,
        val onUserAction: (TradeOrderUiUserAction) -> Unit = {}
    ) : TradeOrderUi() {
        val totalBuyingStockPrice: Double by lazy {
            getTotalBuyingStockPrice(
                currentStockPrice = currentStockPrice,
                stockCountInput = stockCountInput
            )
        }
    }

    object Hide : TradeOrderUi()

    fun show(): Boolean = this !is Hide

    companion object {
        private fun getTotalBuyingStockPrice(
            currentStockPrice: Double,
            stockCountInput: String?
        ): Double = currentStockPrice * (stockCountInput?.toInt() ?: 0)

        fun TradeOrderUi.copy(
            showKeyPad: Boolean? = null,
            maxAvailableStockCount: Int? = null,
            currentStockPrice: Double? = null,
            stockCountInput: String? = null,
            onUserAction: ((TradeOrderUiUserAction) -> Unit)? = null
        ): TradeOrderUi {
            return when (val old = this) {
                is Buy -> {
                    Buy(
                        showKeyPad = showKeyPad ?: old.showKeyPad,
                        maxAvailableStockCount = maxAvailableStockCount
                            ?: old.maxAvailableStockCount,
                        currentStockPrice = currentStockPrice ?: old.currentStockPrice,
                        stockCountInput = stockCountInput ?: old.stockCountInput,
                        onUserAction = onUserAction ?: old.onUserAction
                    )
                }

                is Sell -> {
                    Sell(
                        showKeyPad = showKeyPad ?: old.showKeyPad,
                        maxAvailableStockCount = maxAvailableStockCount
                            ?: old.maxAvailableStockCount,
                        currentStockPrice = currentStockPrice ?: old.currentStockPrice,
                        stockCountInput = stockCountInput ?: old.stockCountInput,
                        onUserAction = onUserAction ?: old.onUserAction
                    )
                }

                is Hide -> {
                    Hide
                }
            }
        }
    }
}

fun List<Tick>.asTransactionVolume(): List<Double> =
    this.map { tick ->
        tick.transactionCount.toDouble()
    }

fun List<Tick>.asCandleStickChartUiState(): CandleStickChartUi =
    CandleStickChartUi(
        opening = this.map { tick -> tick.openPrice.value },
        closing = this.map { tick -> tick.closePrice.value },
        low = this.map { tick -> tick.minPrice.value },
        high = this.map { tick -> tick.maxPrice.value }
    )
