package com.yessorae.domain.exception

sealed class ChartGameException(override val message: String = "") : Exception(message) {
    data class CanNotChangeChartException(
        override val message: String
    ) : ChartGameException(message = message)

    data class CanNotUpdateNextTickException(
        override val message: String
    ) : ChartGameException(message = message)

    // 설정한 제한 보다 많은 재시도를 했음에도 조건에 맞는 차트를 찾지 못했을 때 발생하는 예외
    object HardToFetchTradeException : ChartGameException("")

    data class CanNotChangeTradeException(
        override val message: String
    ) : ChartGameException(message = message)

    data class UnknownException(
        val sight: String
    ) : ChartGameException(message = "occurred at $sight}")
}
