package com.yessorae.domain.exception

sealed class ChartGameException(override val message: String = "") : Exception(message) {
    data class CanNotChangeChartException(
        override val message: String,
    ) : ChartGameException(message)
}
