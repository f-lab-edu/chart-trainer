package com.yessorae.chartrainer.fake

import com.yessorae.domain.common.ChartRequestArgumentHelper

class FakeChartRequestArgumentHelper(
    private val getTicker: (Int) -> String = { "AAPL" },
    private val startDate: String = "2024-05-20",
    private val endDate: String = "2022-05-20"
) : ChartRequestArgumentHelper {
    private var countOfCallingGetRandomTicker = 0

    override fun getRandomTicker(): String {
        countOfCallingGetRandomTicker++
        return getTicker(countOfCallingGetRandomTicker)
    }

    override fun getFromDate(): String = startDate

    override fun getToDate(): String = endDate
}
