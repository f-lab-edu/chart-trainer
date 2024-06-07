package com.yessorae.presentation.ui.screen.chartgame.model

import com.yessorae.domain.common.DefaultValues.defaultTickUnit
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import java.lang.NumberFormatException

data class ChartGameScreenState(
    val currentTurn: Int = 0,
    val totalTurn: Int = 0,
    val totalProfit: Double = 0.0,
    val rateOfProfit: Double = 0.0,
    val gameProgress: Float = 0f,
    val showLoading: Boolean = true,
    val tickUnit: TickUnit = defaultTickUnit,
    // 아래와 같이 라이브러리에 맞춘 형태로 지양하는 UI 모델 형태이다. 변경 고민중.
    val transactionVolume: List<Double> = listOf(),
    val candleStickChart: CandleStickChartUi = CandleStickChartUi(),
    val isGameComplete: Boolean = false,
    val isGameEnd: Boolean = false,
    val tradeOrderUi: TradeOrderUi = TradeOrderUi.Hide,
    val onUserAction: (ChartGameScreenUserAction) -> Unit = {}
) {
    val isBeforeStart = currentTurn <= 1
    val enableChangeChartButton: Boolean = isGameEnd.not()
    val enabledBuyButton: Boolean = (currentTurn > 0 || totalProfit > 0.0) && isGameEnd.not()
    val enabledSellButton: Boolean = (currentTurn > 0 && totalProfit > 0.0) && isGameEnd.not()
    val enabledNextTurnButton: Boolean = currentTurn != totalTurn && isGameEnd.not()
}

data class CandleStickChartUi(
    val opening: List<Double> = listOf(),
    val closing: List<Double> = listOf(),
    val low: List<Double> = listOf(),
    val high: List<Double> = listOf()
) {
    private val isEmpty: Boolean =
        opening.isEmpty() || closing.isEmpty() || low.isEmpty() || high.isEmpty()
}

sealed class TradeOrderUi {
    data class Buy(
        val showKeyPad: Boolean = false,
        val maxAvailableStockCount: Int = 0,
        val currentStockPrice: Double = 0.0,
        val stockCountInput: String = "",
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
        val stockCountInput: String = "",
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
        ): Double {
            val input = try {
                stockCountInput?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }
            return currentStockPrice * input
        }

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
