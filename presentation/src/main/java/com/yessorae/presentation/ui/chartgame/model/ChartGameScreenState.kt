package com.yessorae.presentation.ui.chartgame.model

data class ChartGameScreenState(
    val screenTitle: String,
    val currentTurn: Int,
    val totalTurn: Int,
    val gameProgress: Float,
    val showLoading: Boolean,
    //아래와 같이 라이브러리에 맞춘 형태로 지양하는 UI 모델 형태이다. 변경 고민중.
    val transactionVolume: List<Double>,
    val candleStickChart: CandleStickChartUiState,
)

data class CandleStickChartUiState(
    val opening: List<Double>,
    val closing: List<Double>,
    val log: List<Double>,
    val high: List<Double>,
)
