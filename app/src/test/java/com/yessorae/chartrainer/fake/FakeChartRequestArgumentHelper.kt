package com.yessorae.chartrainer.fake

import com.yessorae.domain.common.ChartRequestArgumentHelper

class FakeChartRequestArgumentHelper : ChartRequestArgumentHelper {
    var currentRandomTicker = ""
    var currentStartDate: String = "2024-05-20"
    var currentEndDate: String = "2022-05-20"

    override fun getRandomTicker(): String {
        return currentRandomTicker
    }

    override fun getFromDate(): String = currentStartDate

    override fun getToDate(): String = currentEndDate
}
