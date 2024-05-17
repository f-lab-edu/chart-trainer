package com.yessorae.domain.exception

sealed class ChartGameException(override val message: String = "") : Exception(message) {
    data class CanNotChangeChartException(
        override val message: String
    ) : ChartGameException(message = message)

    data class CanNotUpdateNextTickException(
        override val message: String
    ) : ChartGameException(message = message)

    data class CanNotChangeTradeException(
        override val message: String
    ) : ChartGameException(message = message)

    data class UnknownException(
        val sight: String
    ) : ChartGameException(message = "occurred at $sight}")
}
